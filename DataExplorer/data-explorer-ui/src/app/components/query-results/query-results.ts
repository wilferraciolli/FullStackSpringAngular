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
  readonly isExecuting = this.store.isExecuting;
  readonly queryError = this.store.queryError;

  readonly defaultColDef: ColDef = {
    sortable: true,
    filter: true,
    resizable: true,
    flex: 1,
    minWidth: 100,
  };

  // Flatten the response: { people: [...], addresses: [...] } → combined flat rows
  readonly rowData = computed(() => {
    const results = this.store.queryResults();
    if (!results) return [];
    // Each query result key returns an array — flatten all into one row set
    return Object.values(results).flat() as any[];
  });

  // Derive column defs from the first row's keys
  readonly colDefs = computed((): ColDef[] => {
    const rows = this.rowData();
    if (rows.length === 0) return [];
    return Object.keys(rows[0]).map((key) => ({
      field: key,
      headerName: key
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, (s) => s.toUpperCase())
        .trim(),
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
}
