import { Component, computed, inject } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridApi, GridReadyEvent, themeQuartz } from 'ag-grid-community';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SchemaStore } from '../../services/query-builder.store';

@Component({
  selector: 'app-query-results',
  imports: [AgGridAngular, MatButtonModule, MatIconModule, MatTooltipModule],
  templateUrl: './query-results.html',
  styleUrl: './query-results.scss',
})
export class QueryResults {
  private readonly store = inject(SchemaStore);
  private gridApi: GridApi | null = null;

  readonly theme = themeQuartz;
  readonly isExecuting    = this.store.isExecuting;
  readonly queryError     = this.store.queryError;
  readonly totalElements  = this.store.totalElements;
  readonly totalPages     = this.store.totalPages;
  readonly currentPage    = this.store.currentPage;

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

  protected exportCsv(): void {
    this.gridApi?.exportDataAsCsv({
      fileName: `export-${new Date().toISOString().substring(0, 10)}.csv`,
    });
  }

  protected prevPage(): void {
    if (this.currentPage() > 0) this.store.changePage(this.currentPage() - 1);
  }

  protected nextPage(): void {
    if (this.currentPage() < this.totalPages() - 1) this.store.changePage(this.currentPage() + 1);
  }
}
