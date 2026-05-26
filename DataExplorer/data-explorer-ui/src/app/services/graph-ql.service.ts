import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import {
  DataArea,
  DataField,
  FieldFilter,
  FilterOperator,
  QueryPayload,
} from '../components/query-builder/query-builder';


@Injectable({
  providedIn: 'root',
})
export class GraphqlService {
  private readonly apiUrl = 'http://localhost:8080/graphql';

  private readonly _httpClient: HttpClient = inject(HttpClient);

  /**   * Extract query types (Person, Address, etc.) and their fields   * to populate DataAreas for the Query Builder   */
  public async extractDataAreasFromSchema(): Promise<DataArea[]> {
    const schema = await this.getSchemaIntrospection();

    const queryType = schema.types.find((t: any) => t.name === 'Query');
    if (!queryType) return [];

    return queryType.fields
      .map((field: any) => {
        // The query now returns PersonPage / AddressPage (not the entity directly).
        // Unwrap: field.type → PersonPage → find its 'content' field → ofType → Person
        const pageTypeName: string = field.type.name ?? field.type.ofType?.name;
        if (!pageTypeName) return null;

        const pageType = schema.types.find(
          (t: any) => t.name === pageTypeName && t.kind === 'OBJECT' && !t.name.startsWith('__'),
        );
        if (!pageType?.fields) return null;

        // Find the 'content' field on PersonPage → its ofType gives the entity type name
        const contentField = pageType.fields.find((f: any) => f.name === 'content');
        if (!contentField) return null;

        // content is [Person] — unwrap LIST then NON_NULL wrappers to reach 'Person'
        const entityTypeName: string =
          contentField.type?.ofType?.name ??           // [Person]
          contentField.type?.ofType?.ofType?.name ??   // [Person!]
          null;
        if (!entityTypeName) return null;

        const entityType = schema.types.find(
          (t: any) => t.name === entityTypeName && t.kind === 'OBJECT' && !t.name.startsWith('__'),
        );
        if (!entityType?.fields) return null;

        // Filter input: PersonFilter for Person, AddressFilter for Address etc.
        const filterTypeName = `${entityTypeName}Filter`;
        const filterType = schema.types.find(
          (t: any) => t.name === filterTypeName && t.kind === 'INPUT_OBJECT',
        );
        const filterableNames = new Set<string>(
          (filterType?.inputFields ?? [])
            .map((f: any) => f.name as string)
            .filter((name: string) => name !== 'effectiveDate'),
        );

        return {
          key: entityTypeName.toLowerCase(),
          label: entityTypeName,
          icon: this.getIconForType(entityTypeName),
          queryName: field.name,
          fields: entityType.fields.map((f: any) => ({
            key: `${entityTypeName.toLowerCase()}.${f.name}`,
            label: this.formatLabel(f.name),
            type: this.resolveScalarType(f.type, f.name),
            filterable: filterableNames.has(f.name),
          })),
        } as DataArea;
      })
      .filter((area: DataArea | null): area is DataArea => area !== null);
  }

  public async executeQuery(dataAreas: DataArea[], payload: QueryPayload): Promise<any> {
    const query = this.buildGraphQLQuery(dataAreas, payload);
    console.log('Query built before sending request ', query);
    const response = await firstValueFrom(this._httpClient.post<any>(this.apiUrl, { query }));
    if (response.errors?.length) {
      throw new Error(response.errors.map((e: any) => e.message).join(', '));
    }
    return response.data;
  }

  private buildGraphQLQuery(dataAreas: DataArea[], payload: QueryPayload): string {
    const { fieldKeys, filters, dateRange, page, pageSize } = payload;

    // Map area key → GraphQL query name  (e.g. person → people)
    const queryNameMap = dataAreas.reduce<Record<string, string>>((acc, a) => {
      acc[a.key] = a.queryName;
      return acc;
    }, {});

    // Group selected field names by area key
    const grouped = fieldKeys.reduce<Record<string, string[]>>((acc, key) => {
      const [areaKey, fieldName] = key.split('.');
      (acc[areaKey] ??= []).push(fieldName);
      return acc;
    }, {});

    // Only include filters that have an operator and (where needed) a value
    const filtersByArea = filters
      .filter((f) => f.operator !== '' && (f.operator === 'is_null' || f.operator === 'is_not_null' || f.value.trim() !== ''))
      .reduce<Record<string, FieldFilter[]>>((acc, f) => {
        const [areaKey] = f.fieldKey.split('.');
        (acc[areaKey] ??= []).push(f);
        return acc;
      }, {});

    const blocks = Object.entries(grouped)
      .map(([areaKey, fields]) => {
        const queryName = queryNameMap[areaKey] ?? areaKey;

        // Indent selected fields inside content { }
        const fieldList = fields.map((f) => `      ${f}`).join('\n');

        // Per-field filter parts
        const fieldFilterParts = (filtersByArea[areaKey] ?? []).map((f) => {
          const fieldName = f.fieldKey.split('.')[1];
          return `${fieldName}: { ${this.serializeOperator(f.operator, f.value)} }`;
        });

        // effectiveDate date-range filter (only if the area has the field)
        const area = dataAreas.find((a) => a.key === areaKey);
        const hasEffectiveDate = area?.fields.some((f) => f.key === `${areaKey}.effectiveDate`);
        if (hasEffectiveDate) {
          fieldFilterParts.push(`effectiveDate: { after: "${dateRange.after}", before: "${dateRange.before}" }`);
        }

        const filterArg = fieldFilterParts.length > 0
          ? `filter: { ${fieldFilterParts.join(', ')} }, `
          : '';

        // page argument — always sent so the server applies LIMIT/OFFSET
        const pageArg = `page: { page: ${page}, size: ${pageSize} }`;

        return `  ${queryName}(${filterArg}${pageArg}) {\n    content {\n${fieldList}\n    }\n    totalElements\n    totalPages\n    page\n    size\n  }`;
      })
      .join('\n');

    return `{\n${blocks}\n}`;
  }

  private serializeOperator(operator: FilterOperator, value: string): string {
    switch (operator) {
      case 'equals':
        return `equals: "${value}"`;
      case 'not_equals':
        return `notEquals: "${value}"`;
      case 'contains':
        return `contains: "${value}"`;
      case 'starts_with':
        return `startsWith: "${value}"`;
      case 'greater_than':
        return `after: "${value}"`;
      case 'less_than':
        return `before: "${value}"`;
      case 'is_null':
        return `isNull: true`;
      case 'is_not_null':
        return `isNull: false`;
      default:
        return '';
    }
  }

  private resolveScalarType(type: any, fieldName?: string): DataField['type'] {
    // Unwrap NON_NULL wrapper if present
    const unwrapped = type.kind === 'NON_NULL' ? type.ofType : type;
    const name = unwrapped?.name ?? unwrapped?.ofType?.name ?? 'unknown';

    if (name === 'ID') return 'ID';
    if (name === 'Boolean') return 'Boolean';
    if (['Date', 'LocalDate', 'DateTime'].includes(name)) return 'Date';

    // Hint: treat known date-named String fields as Date
    if (name === 'String' && fieldName && /date|Date/.test(fieldName)) return 'Date';

    if (name === 'String') return 'String';
    return 'unknown';
  }

  /**   * Fetch GraphQL schema introspection to discover available types and fields   */
  private async getSchemaIntrospection(): Promise<any> {
    const introspectionQuery = `
      query IntrospectionQuery {
        __schema {
          types {
            name
            kind
            fields(includeDeprecated: false) {
              name
              type {
                name
                kind
                ofType {
                  name
                  kind
                }
              }
            }
            inputFields {
              name
              type {
                name
                kind
                ofType {
                  name
                  kind
                }
              }
            }
          }
        }
      }
    `;

    try {
      const response = await firstValueFrom(
        this._httpClient.post<any>(this.apiUrl, {
          query: introspectionQuery,
        }),
      );

      return response.data.__schema;
    } catch (error) {
      console.error('Failed to fetch GraphQL schema:', error);
      throw error;
    }
  }

  private getIconForType(typeName: string): string {
    const iconMap: Record<string, string> = {
      Person: 'person',
      Address: 'location_on',
    };
    return iconMap[typeName] ?? 'data_object';
  }

  private formatLabel(fieldName: string): string {
    return fieldName
      .replace(/([A-Z])/g, ' $1')
      .replace(/^./, (str) => str.toUpperCase())
      .trim();
  }
}
