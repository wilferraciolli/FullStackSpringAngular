import { Component, computed, inject, signal } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridApi, GridReadyEvent, themeQuartz } from 'ag-grid-community';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SchemaStore } from '../../services/query-builder.store';
import { GraphqlService } from '../../services/graph-ql.service';
import { QueryPayload } from '../query-builder/query-builder';

@Component({
  selector: 'app-query-results',
  imports: [AgGridAngular, MatButtonModule, MatIconModule, MatTooltipModule, MatProgressSpinnerModule],
  templateUrl: './query-results.html',
  styleUrl: './query-results.scss',
})
export class QueryResults {
  private readonly store = inject(SchemaStore);
  private readonly graphqlService = inject(GraphqlService);
  private gridApi: GridApi | null = null;

  readonly theme = themeQuartz;
  readonly isExecuting    = this.store.isExecuting;
  readonly queryError     = this.store.queryError;
  readonly totalElements  = this.store.totalElements;
  readonly totalPages     = this.store.totalPages;
  readonly currentPage    = this.store.currentPage;
  readonly isExporting    = signal(false);

  readonly defaultColDef: ColDef = {
    sortable: true, filter: true, resizable: true, flex: 1, minWidth: 100,
  };

  // Extract rows from content[] inside each area result (e.g. { people: { content: [...] } })
  readonly rowData = computed(() => {
    const results = this.store.queryResults();
    if (!results) return [];
    return Object.values(results).flatMap((r: any) =>
      Array.isArray(r?.content) ? r.content : (Array.isArray(r) ? r : [])
    ) as any[];
  });

  readonly colDefs = computed((): ColDef[] => {
    const rows = this.rowData();
    if (rows.length === 0) return [];
    return Object.keys(rows[0]).map((key) => ({
      field: key,
      headerName: key.replace(/([A-Z])/g, ' $1').replace(/^./, (s) => s.toUpperCase()).trim(),
    }));
  });

  protected onGridReady(event: GridReadyEvent): void {
    this.gridApi = event.api;
  }

  protected async exportAllCsv(): Promise<void> {
    const last = this.store.lastPayload();
    if (!last || this.isExporting()) return;

    this.isExporting.set(true);
    try {
      // Re-run the same query but ask for up to 10 000 rows on page 0
      const allDataPayload: QueryPayload = { ...last, page: 0, pageSize: 10_000 };
      const result = await this.graphqlService.executeQuery(this.store.dataAreas(), allDataPayload);

      // Pull rows out of the first area's content array
      const rows: any[] = (Object.values(result ?? {})[0] as any)?.content ?? [];
      if (rows.length === 0) return;

      const fieldKeys = Object.keys(rows[0]);
      const header = fieldKeys.join(',');
      const lines = rows.map((r) =>
        fieldKeys.map((k) => `"${String(r[k] ?? '').replaceAll('"', '""')}"`).join(','),
      );
      const csv = [header, ...lines].join('\n');

      const blob = new Blob([csv], { type: 'text/csv' });
      const url  = URL.createObjectURL(blob);
      const anchor = document.createElement('a');
      anchor.href     = url;
      anchor.download = `export-${new Date().toISOString().substring(0, 10)}.csv`;
      anchor.click();
      URL.revokeObjectURL(url);
    } catch (e) {
      console.error('CSV export failed', e);
    } finally {
      this.isExporting.set(false);
    }
  }

  protected prevPage(): void {
    if (this.currentPage() > 0) this.store.changePage(this.currentPage() - 1);
  }

  protected nextPage(): void {
    if (this.currentPage() < this.totalPages() - 1) this.store.changePage(this.currentPage() + 1);
  }
}
