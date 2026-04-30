import { Component, signal, computed } from '@angular/core';
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

export interface DataField {
  key: string;
  label: string;
  selected: boolean;
}

export interface IndexedField extends DataField {
  areaKey: string;
  areaLabel: string;
}

export interface DataArea {
  key: string;
  label: string;
  icon: string;
  fields: DataField[];
}

export type FilterOperator =
  | '' | 'is_null' | 'is_not_null'
  | 'equals' | 'not_equals'
  | 'greater_than' | 'less_than'
  | 'contains' | 'starts_with';

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
  ],
  templateUrl: './query-builder.html',
  styleUrl: './query-builder.scss',
})
export class QueryBuilder {
  aiPrompt = signal('');
  selectedArea = signal<DataArea | null>(null);

  // Date range
  dateRangeOption = signal<DateRangeOption>('this_month');
  customDateStart: Date | null = null;
  customDateEnd: Date | null = null;

  // Filter search
  filterSearchTerm = signal('');

  readonly dateRangeOptions: { value: DateRangeOption; label: string }[] = [
    { value: 'custom',     label: 'Custom' },
    { value: 'today',      label: 'Today' },
    { value: 'this_week',  label: 'This Week' },
    { value: 'this_month', label: 'This Month' },
  ];

  readonly filterOperators: { value: FilterOperator; label: string }[] = [
    { value: '',            label: '— None —' },
    { value: 'is_null',     label: 'Is Null' },
    { value: 'is_not_null', label: 'Is Not Null' },
    { value: 'equals',      label: 'Equals' },
    { value: 'not_equals',  label: 'Not Equals' },
    { value: 'greater_than',label: 'Greater Than' },
    { value: 'less_than',   label: 'Less Than' },
    { value: 'contains',    label: 'Contains' },
    { value: 'starts_with', label: 'Starts With' },
  ];

  // Ordered list of added filter keys (preserves insertion order)
  addedFilterKeys = signal<string[]>([]);

  // Map: fieldKey → FieldFilter
  fieldFilters = signal<Map<string, FieldFilter>>(new Map());

  dataAreas: DataArea[] = [
    {
      key: 'person',
      label: 'Person',
      icon: 'person',
      fields: [
        { key: 'person.id',        label: 'ID',            selected: false },
        { key: 'person.firstName', label: 'First Name',    selected: false },
        { key: 'person.lastName',  label: 'Last Name',     selected: false },
        { key: 'person.email',     label: 'Email',         selected: false },
        { key: 'person.dob',       label: 'Date of Birth', selected: false },
        { key: 'person.phone',     label: 'Phone',         selected: false },
      ],
    },
    {
      key: 'absence',
      label: 'Absence',
      icon: 'event_busy',
      fields: [
        { key: 'absence.personId', label: 'Person',               selected: false },
        { key: 'absence.dayStart', label: 'Day Start',            selected: false },
        { key: 'absence.dayEnd',   label: 'Day End',              selected: false },
        { key: 'absence.type',     label: 'Type (e.g. Sickness)', selected: false },
        { key: 'absence.notes',    label: 'Notes',                selected: false },
      ],
    },
    {
      key: 'jobs',
      label: 'Jobs',
      icon: 'work',
      fields: [
        { key: 'jobs.jobId',      label: 'Job ID',     selected: false },
        { key: 'jobs.title',      label: 'Job Title',  selected: false },
        { key: 'jobs.department', label: 'Department', selected: false },
        { key: 'jobs.location',   label: 'Location',   selected: false },
        { key: 'jobs.startDate',  label: 'Start Date', selected: false },
        { key: 'jobs.salary',     label: 'Salary',     selected: false },
      ],
    },
  ];

  /** Flat list of every field across all areas */
  get allIndexedFields(): IndexedField[] {
    return this.dataAreas.flatMap(area =>
      area.fields.map(f => ({ ...f, areaKey: area.key, areaLabel: area.label }))
    );
  }

  /** Fields matching the search term, excluding already-added ones */
  get fieldSuggestions(): IndexedField[] {
    const term = this.filterSearchTerm().toLowerCase().trim();
    const added = new Set(this.addedFilterKeys());
    return this.allIndexedFields.filter(f =>
      !added.has(f.key) &&
      (term === '' || f.label.toLowerCase().includes(term) || f.areaLabel.toLowerCase().includes(term))
    );
  }

  /** Ordered list of FieldFilter objects currently in the list */
  get addedFilters(): FieldFilter[] {
    const map = this.fieldFilters();
    return this.addedFilterKeys()
      .map(k => map.get(k))
      .filter((f): f is FieldFilter => f !== undefined);
  }

  /** Filters that have a non-empty operator */
  get activeFilters(): FieldFilter[] {
    return this.addedFilters.filter(f => f.operator !== '');
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
    this.addedFilterKeys.set(this.addedFilterKeys().filter(k => k !== fieldKey));
    const map = new Map(this.fieldFilters());
    map.delete(fieldKey);
    this.fieldFilters.set(map);
  }

  onOperatorChange(filter: FieldFilter, op: FilterOperator) {
    const map = new Map(this.fieldFilters());
    map.set(filter.fieldKey, { ...filter, operator: op, value: this.needsNoValue(op) ? '' : filter.value });
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
    return this.dateRangeOptions.find(o => o.value === this.dateRangeOption())?.label ?? '';
  }

  get selectedFields(): DataField[] {
    return this.selectedArea()?.fields.filter(f => f.selected) ?? [];
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
      fields: this.selectedFields.map(f => f.key),
      dateRange: this.dateRangeOption(),
      customDateStart: this.customDateStart,
      customDateEnd: this.customDateEnd,
      filters: this.activeFilters,
    });
  }

  runQuery() {
    console.log('[Query Builder] Run query:', {
      fields: this.selectedFields.map(f => f.key),
      dateRange: this.dateRangeOption(),
      customDateStart: this.customDateStart,
      customDateEnd: this.customDateEnd,
      filters: this.activeFilters,
    });
  }
}
