import { Component, inject, signal, Signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CdkDragDrop, DragDropModule, moveItemInArray } from '@angular/cdk/drag-drop';
import { SchemaStore } from '../../services/query-builder.store';
import { QueryResults } from '../query-results/query-results';

export interface DataField {
  key: string;
  label: string;
  type: 'String' | 'Date' | 'ID' | 'Boolean' | 'unknown';
}

export interface IndexedField extends DataField {
  areaKey: string;
  areaLabel: string;
}

export interface DataArea {
  key: string;
  label: string;
  icon: string;
  queryName: string;
  fields: DataField[];
}

export interface QueryPayload {
  fieldKeys: string[];
  filters: FieldFilter[];
}

export type FilterOperator =
  | ''
  | 'is_null'
  | 'is_not_null'
  | 'equals'
  | 'not_equals'
  | 'greater_than'
  | 'less_than'
  | 'contains'
  | 'starts_with';

export interface FieldFilter {
  fieldKey: string;
  fieldLabel: string;
  areaLabel: string;
  operator: FilterOperator;
  value: string;
}

export type DateRangeOption = 'today' | 'this_week' | 'this_month' | 'custom';

@Component({
  selector: 'app-query-builder',
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatListModule,
    MatCheckboxModule,
    MatDividerModule,
    MatChipsModule,
    MatExpansionModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatAutocompleteModule,
    MatTooltipModule,
    DragDropModule,
    QueryResults,
  ],
  templateUrl: './query-builder.html',
  styleUrl: './query-builder.scss',
})
export class QueryBuilder {
  private readonly _schemaStore = inject(SchemaStore);

  protected aiPrompt: WritableSignal<string> = signal('');
  protected dataAreas: Signal<DataArea[]> = this._schemaStore.dataAreas;
  protected selectedArea: WritableSignal<DataArea | null> = signal<DataArea | null>(null);

  protected readonly isLoadingAreas: Signal<boolean> = this._schemaStore.isLoading;
  protected readonly loadingError: Signal<string | null> = this._schemaStore.error;

  protected readonly queryResults: Signal<any> = this._schemaStore.queryResults;
  protected readonly isExecuting: Signal<boolean> = this._schemaStore.isExecuting;
  protected readonly queryError: Signal<string | null> = this._schemaStore.queryError;

  // Ordered list of selected field keys (drives both display order and drag-drop)
  selectedFieldKeys = signal<string[]>([]);

  // Date range
  dateRangeOption = signal<DateRangeOption>('this_month');
  customDateStart: Date | null = null;
  customDateEnd: Date | null = null;

  // Filter search
  filterSearchTerm = signal('');

  readonly dateRangeOptions: { value: DateRangeOption; label: string }[] = [
    { value: 'custom', label: 'Custom' },
    { value: 'today', label: 'Today' },
    { value: 'this_week', label: 'This Week' },
    { value: 'this_month', label: 'This Month' },
  ];

  protected readonly operatorsByType: Record<string, { value: FilterOperator; label: string }[]> = {
    String: [
      { value: '', label: '— None —' },
      { value: 'equals', label: 'Equals' },
      { value: 'not_equals', label: 'Not Equals' },
      { value: 'contains', label: 'Contains' },
      { value: 'starts_with', label: 'Starts With' },
      { value: 'is_null', label: 'Is Null' },
      { value: 'is_not_null', label: 'Is Not Null' },
    ],
    Date: [
      { value: '',            label: '— None —' },
      { value: 'equals',      label: 'On' },
      { value: 'greater_than', label: 'After' },
      { value: 'less_than',   label: 'Before' },
      { value: 'is_null',     label: 'Is Null' },
      { value: 'is_not_null', label: 'Is Not Null' },
    ],
    ID: [
      { value: '', label: '— None —' },
      { value: 'equals', label: 'Equals' },
      { value: 'not_equals', label: 'Not Equals' },
      { value: 'is_null', label: 'Is Null' },
      { value: 'is_not_null', label: 'Is Not Null' },
    ],
    unknown: [
      { value: '', label: '— None —' },
      { value: 'equals', label: 'Equals' },
      { value: 'not_equals', label: 'Not Equals' },
      { value: 'is_null', label: 'Is Null' },
      { value: 'is_not_null', label: 'Is Not Null' },
    ],
  };

  // Ordered list of added filter keys (preserves insertion order)
  addedFilterKeys = signal<string[]>([]);

  // Map: fieldKey → FieldFilter
  fieldFilters = signal<Map<string, FieldFilter>>(new Map());

  getOperatorsForFilter(filter: FieldFilter): { value: FilterOperator; label: string }[] {
    const field = this.allIndexedFields.find((f) => f.key === filter.fieldKey);

    return this.operatorsByType[field?.type ?? 'unknown'] ?? this.operatorsByType['unknown'];
  }

  /** Flat list of every field across all areas */
  get allIndexedFields(): IndexedField[] {
    return this.dataAreas().flatMap((area) =>
      area.fields.map((f) => ({ ...f, areaKey: area.key, areaLabel: area.label })),
    );
  }

  /** Fields matching the search term, excluding already-added ones */
  get fieldSuggestions(): IndexedField[] {
    const term = this.filterSearchTerm().toLowerCase().trim();
    const added = new Set(this.addedFilterKeys());
    const currentAreaKey = this.selectedArea()?.key;

    return this.allIndexedFields.filter(
      (f) =>
        f.areaKey === currentAreaKey &&
        !added.has(f.key) &&
        (term === '' ||
          f.label.toLowerCase().includes(term) ||
          f.areaLabel.toLowerCase().includes(term)),
    );
  }

  /** Ordered list of FieldFilter objects currently in the list */
  get addedFilters(): FieldFilter[] {
    const map = this.fieldFilters();
    return this.addedFilterKeys()
      .map((k) => map.get(k))
      .filter((f): f is FieldFilter => f !== undefined);
  }

  /** Filters that have a non-empty operator */
  get activeFilters(): FieldFilter[] {
    return this.addedFilters.filter((f) => f.operator !== '');
  }

  addFieldToFilter(field: IndexedField) {
    if (this.addedFilterKeys().includes(field.key)) return;
    const map = new Map(this.fieldFilters());
    map.set(field.key, {
      fieldKey: field.key,
      fieldLabel: field.label,
      areaLabel: field.areaLabel,
      operator: '',
      value: '',
    });
    this.fieldFilters.set(map);
    this.addedFilterKeys.set([...this.addedFilterKeys(), field.key]);
    this.filterSearchTerm.set('');
  }

  removeFilter(fieldKey: string) {
    this.addedFilterKeys.set(this.addedFilterKeys().filter((k) => k !== fieldKey));
    const map = new Map(this.fieldFilters());
    map.delete(fieldKey);
    this.fieldFilters.set(map);
  }

  onOperatorChange(filter: FieldFilter, op: FilterOperator) {
    const map = new Map(this.fieldFilters());
    map.set(filter.fieldKey, {
      ...filter,
      operator: op,
      value: this.needsNoValue(op) ? '' : filter.value,
    });
    this.fieldFilters.set(map);
  }

  onFilterValueChange(filter: FieldFilter, value: string) {
    const map = new Map(this.fieldFilters());
    map.set(filter.fieldKey, { ...filter, value });
    this.fieldFilters.set(map);
  }

  needsNoValue(op: FilterOperator): boolean {
    return op === 'is_null' || op === 'is_not_null' || op === '';
  }

  get dateRangeLabel(): string {
    return this.dateRangeOptions.find((o) => o.value === this.dateRangeOption())?.label ?? '';
  }

  get selectedFields(): IndexedField[] {
    const keyOrder = this.selectedFieldKeys();
    const allFields = this.allIndexedFields;
    return keyOrder
      .map((k) => allFields.find((f) => f.key === k))
      .filter((f): f is IndexedField => f !== undefined);
  }

  protected onFieldToggle(field: DataField, checked: boolean): void {
    if (checked) {
      if (!this.selectedFieldKeys().includes(field.key)) {
        this.selectedFieldKeys.set([...this.selectedFieldKeys(), field.key]);
      }
    } else {
      this.selectedFieldKeys.set(this.selectedFieldKeys().filter((k) => k !== field.key));
    }
  }

  protected removeSelectedField(fieldKey: string): void {
    // Uncheck the field object so the checkbox reflects removal
    this.selectedFieldKeys.set(this.selectedFieldKeys().filter((k) => k !== fieldKey));
  }

  protected isFieldSelected(fieldKey: string): boolean {
    return this.selectedFieldKeys().includes(fieldKey);
  }

  protected onAreaChange(area: DataArea | null): void {
    const current = this.selectedArea();

    if (current && area?.key !== current.key) {
      // clear selections when area changes
      this.selectedFieldKeys.set([]);
      this.addedFilterKeys.set([]);
      this.fieldFilters.set(new Map());
      this.filterSearchTerm.set('');
    }
    this.selectedArea.set(area);
  }

  onFieldDrop(event: CdkDragDrop<string[]>) {
    const keys = [...this.selectedFieldKeys()];
    moveItemInArray(keys, event.previousIndex, event.currentIndex);
    this.selectedFieldKeys.set(keys);
  }

  selectArea(area: DataArea) {
    this.selectedArea.set(this.selectedArea()?.key === area.key ? null : area);
  }

  compareAreas(a: DataArea | null, b: DataArea | null): boolean {
    return a?.key === b?.key;
  }

  buildWithAi() {
    console.log('[AI Query Builder] Prompt:', this.aiPrompt());
  }

  saveQuery() {
    console.log('[Query Builder] Save query:', {
      fields: this.selectedFields.map((f) => f.key),
      dateRange: this.dateRangeOption(),
      customDateStart: this.customDateStart,
      customDateEnd: this.customDateEnd,
      filters: this.activeFilters,
    });
  }

  protected runQuery(): void {
    console.log('[Query Builder] Run query:', {
      fields: this.selectedFields.map((f) => f.key),
      dateRange: this.dateRangeOption(),
      customDateStart: this.customDateStart,
      customDateEnd: this.customDateEnd,
      filters: this.activeFilters,
    });
    // const query = this._graphqlService.buildGraphQLQuery(this.selectedFields.map((f) => f.key));
    this._schemaStore.runQuery({
      fieldKeys: this.selectedFields.map((f) => f.key),
      filters: this.activeFilters,
    });
    // console.log('Result ', query);
  }
}
