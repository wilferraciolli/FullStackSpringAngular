import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { DataArea, FilterOperator } from '../components/query-builder/query-builder';

export type AiAvailability = 'checking' | 'ready' | 'needs-download' | 'unavailable';

export interface ParsedQuery {
  area: string;
  fieldKeys: string[];
  filters: Array<{ fieldKey: string; operator: FilterOperator; value: string }>;
}

/**
 * Parses a natural-language prompt into a structured query.
 *
 * Strategy (in order):
 *  1. Chrome's built-in AI (window.LanguageModel / Gemini Nano) — zero cost, fully local.
 *  2. Groq API via the Spring Boot backend (/api/ai/parse-query) — requires ai.groq.api-key in application.yml.
 */
@Injectable({ providedIn: 'root' })
export class AiQueryService {
  private readonly _http = inject(HttpClient);

  /** Current availability of Chrome's built-in AI. */
  readonly availability = signal<AiAvailability>('checking');

  constructor() {
    this._checkChromeAi();
  }

  private async _checkChromeAi(): Promise<void> {
    const lm = (window as any).LanguageModel;
    if (!lm) {
      this.availability.set('unavailable');
      return;
    }
    try {
      const status: string = await lm.availability();
      console.debug('[ChromeAI] availability:', status);
      // status values: 'readily', 'available' (needs download), 'unavailable', 'after-download'
      if (status === 'readily') {
        this.availability.set('ready');
      } else if (status === 'available' || status === 'after-download') {
        this.availability.set('needs-download');
      } else {
        this.availability.set('unavailable');
      }
    } catch (e) {
      console.warn('[ChromeAI] availability check failed:', e);
      this.availability.set('unavailable');
    }
  }

  async parsePrompt(prompt: string, dataAreas: DataArea[]): Promise<ParsedQuery> {
    const lm = (window as any).LanguageModel;
    const status = this.availability();

    // Only use Chrome AI when the model is already on device ('ready')
    // If it 'needs-download', fall through to Groq to avoid a silent hang
    if (lm && status === 'ready') {
      return this._withChromeAi(prompt, dataAreas, lm);
    }

    // Groq backend fallback
    return firstValueFrom(
      this._http.post<ParsedQuery>('http://localhost:8080/api/ai/parse-query', {
        prompt,
        schema: dataAreas.map(a => ({ key: a.key, label: a.label, fields: a.fields })),
      }),
    );
  }

  private async _withChromeAi(prompt: string, dataAreas: DataArea[], lm: any): Promise<ParsedQuery> {
    const timeout = AbortSignal.timeout(30_000); // 30 s hard limit

    console.time('[ChromeAI] create session');
    const session = await lm.create({
      systemPrompt: this._buildSystemPrompt(dataAreas),
      expectedInputLanguages: ['en'],
      expectedOutputLanguages: ['en'],
      signal: timeout,
    });
    console.timeEnd('[ChromeAI] create session');

    try {
      console.time('[ChromeAI] prompt');
      const raw: string = await session.prompt(prompt, { signal: timeout });
      console.timeEnd('[ChromeAI] prompt');
      console.debug('[ChromeAI] raw response:', raw);

      // Strip markdown code fences some models add (```json ... ```)
      const json = raw.replace(/^```(?:json)?\s*/i, '').replace(/```\s*$/, '').trim();
      return JSON.parse(json) as ParsedQuery;
    } finally {
      session.destroy();
    }
  }

  private _buildSystemPrompt(dataAreas: DataArea[]): string {
    const schemaLines = dataAreas
      .map(area => {
        const all = area.fields.map(f => f.key).join(', ');
        const filterable = area.fields
          .filter(f => f.filterable)
          .map(f => `${f.key} (${f.type})`)
          .join(', ');
        return `Area "${area.key}": allFields=[${all}], filterableFields=[${filterable}]`;
      })
      .join('\n');

    return `You are a query builder assistant. Parse the user's natural language into a structured JSON query.

Schema:
${schemaLines}

Valid operators: equals, not_equals, contains, starts_with, is_null, is_not_null, greater_than, less_than

Return ONLY a raw JSON object — no explanation, no markdown, no code fences:
{"area":"<areaKey>","fieldKeys":["<fieldKey>",...],"filters":[{"fieldKey":"<fieldKey>","operator":"<operator>","value":"<value>"}]}

Rules:
- area must be one of the available area keys
- fieldKeys must be exact keys from allFields (e.g. "person.email")
- filters must only use filterable fields
- value must be empty string ("") for is_null and is_not_null operators
- include in fieldKeys any fields mentioned in the query plus common identifying fields`;
  }
}

