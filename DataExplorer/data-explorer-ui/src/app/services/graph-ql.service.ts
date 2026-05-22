import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { DataArea } from '../components/query-builder/query-builder';


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
            selected: false,
          })),
        } as DataArea;
      })
      .filter((area: DataArea | null): area is DataArea => area !== null);
  }

  public async executeQuery(selectedFieldKeys: string[], dataAreas: DataArea[]): Promise<any> {
    const query = this.buildGraphQLQuery(selectedFieldKeys, dataAreas);
    console.log('Query built before sending request ', query);
    const response = await firstValueFrom(this._httpClient.post<any>(this.apiUrl, { query }));

    if (response.errors?.length) {
      throw new Error(response.errors.map((e: any) => e.message).join(', '));
    }

    return response.data;
  }

  private buildGraphQLQuery(selectedFieldKeys: string[], dataAreas: DataArea[]): string {
    // Map from area key to GraphQL query name: person → people, address → addresses
    const queryNameMap: Record<string, string> = dataAreas.reduce<Record<string, string>>(
      (acc, area) => {
        acc[area.key] = area.queryName;
        return acc;
      },
      {},
    );

    // Group field names by area key
    // e.g. { person: ['email', 'firstName'], address: ['city'] }
    const grouped = selectedFieldKeys.reduce<Record<string, string[]>>((acc, key) => {
      const [areaKey, fieldName] = key.split('.');
      if (!acc[areaKey]) acc[areaKey] = [];
      acc[areaKey].push(fieldName);
      return acc;
    }, {});

    // Build each query block
    const blocks = Object.entries(grouped)
      .map(([areaKey, fields]) => {
        const queryName = queryNameMap[areaKey] ?? areaKey;
        const fieldList = fields.map((f) => `    ${f}`).join('\n');
        return `  ${queryName} {\n${fieldList}\n  }`;
      })
      .join('\n');

    return `{\n${blocks}\n}`;
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
