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

  /**   * Fetch GraphQL schema introspection to discover available types and fields   */
  async getSchemaIntrospection(): Promise<any> {
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

  /**   * Extract query types (Person, Address, etc.) and their fields   * to populate DataAreas for the Query Builder   */
  async extractDataAreasFromSchema(): Promise<DataArea[]> {
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
          fields: returnType.fields.map((f: any) => ({
            key: `${returnTypeName.toLowerCase()}.${f.name}`,
            label: this.formatLabel(f.name),
            selected: false,
          })),
        } as DataArea;
      })
      .filter((area: DataArea | null): area is DataArea => area !== null);
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
