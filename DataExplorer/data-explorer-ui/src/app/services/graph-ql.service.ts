import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import {
  DataArea,
  DataField,
  FieldFilter,
  FilterOperator,
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
        // Unwrap LIST wrapper: type.name is null when kind=LIST, real name is in ofType
        const returnTypeName: string = field.type.name ?? field.type.ofType?.name;

        if (!returnTypeName) return null;

        // Only map to user-defined OBJECT types (skip scalars, enums, __ internals)
        const returnType = schema.types.find(
          (t: any) => t.name === returnTypeName && t.kind === 'OBJECT' && !t.name.startsWith('__'),
        );

        if (!returnType?.fields) return null;

        return {
          key: returnTypeName.toLowerCase(),
          label: returnTypeName,
          icon: this.getIconForType(returnTypeName),
          queryName: field.name,
          fields: returnType.fields.map((f: any) => ({
            key: `${returnTypeName.toLowerCase()}.${f.name}`,
            label: this.formatLabel(f.name),
            type: this.resolveScalarType(f.type, f.name),
          })),
        } as DataArea;
      })
      .filter((area: DataArea | null): area is DataArea => area !== null);
  }

  public async executeQuery(
    selectedFieldKeys: string[],
    dataAreas: DataArea[],
    filters: FieldFilter[],
    dateRange: { before: string; after: string;  },
  ): Promise<any> {
    const query = this.buildGraphQLQuery(selectedFieldKeys, dataAreas, filters, dateRange);
    console.log('Query built before sending request ', query);
    const response = await firstValueFrom(this._httpClient.post<any>(this.apiUrl, { query }));

    if (response.errors?.length) {
      throw new Error(response.errors.map((e: any) => e.message).join(', '));
    }

    return response.data;
  }

  private buildGraphQLQuery(
    selectedFieldKeys: string[],
    dataAreas: DataArea[],
    filters: FieldFilter[],
    dateRange: { before: string; after: string;  },
  ): string {
    // Map from area key to GraphQL query name: person → people, address → addresses
    const queryNameMap: Record<string, string> = dataAreas.reduce<Record<string, string>>(
      (acc, area) => {
        acc[area.key] = area.queryName;
        return acc;
      },
      {},
    );

    const grouped = selectedFieldKeys.reduce<Record<string, string[]>>((acc, key) => {
      const [areaKey, fieldName] = key.split('.');
      if (!acc[areaKey]) acc[areaKey] = [];
      acc[areaKey].push(fieldName);
      return acc;
    }, {});

    // Only include filters that have an operator set
    const filtersByArea = filters
      .filter((f) => {
        if (f.operator === '') return false;
        if (f.operator === 'is_null' || f.operator === 'is_not_null') return true; // no value needed
        return f.value.trim() !== ''; // all others need a value
      })
      .reduce<Record<string, FieldFilter[]>>((acc, f) => {
        const [areaKey] = f.fieldKey.split('.');
        if (!acc[areaKey]) acc[areaKey] = [];
        acc[areaKey].push(f);
        return acc;
      }, {});

    const blocks = Object.entries(grouped)
      .map(([areaKey, fields]) => {
        const queryName = queryNameMap[areaKey] ?? areaKey;
        const fieldList = fields.map((f) => `    ${f}`).join('\n');

        const areaFilters = filtersByArea[areaKey] ?? [];

        // Per-field filter parts
        const fieldFilterParts = areaFilters.map((f) => {
          const fieldName = f.fieldKey.split('.')[1];
          return `${fieldName}: { ${this.serializeOperator(f.operator, f.value)} }`;
        });

        // Date range → effectiveDate filter (only for areas that expose effectiveDate)
        const area = dataAreas.find((a) => a.key === areaKey);
        const hasEffectiveDate = area?.fields.some((f) => f.key === `${areaKey}.effectiveDate`);
        const dateFilterParts: string[] = [];
        if (hasEffectiveDate) {
          dateFilterParts.push(`effectiveDate: { after: "${dateRange.after}", before: "${dateRange.before}" }`);
        }

        const allFilterParts = [...fieldFilterParts, ...dateFilterParts];
        const filterArg = allFilterParts.length > 0
          ? `(filter: { ${allFilterParts.join(', ')} })`
          : '';

        return `  ${queryName}${filterArg} {\n${fieldList}\n  }`;
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
    const introspectionQuery = `      query IntrospectionQuery {
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
