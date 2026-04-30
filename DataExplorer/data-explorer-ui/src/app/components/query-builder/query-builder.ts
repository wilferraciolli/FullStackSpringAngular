import { Component, signal } from '@angular/core';
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

export interface DataField {
  key: string;
  label: string;
  selected: boolean;
}

export interface DataArea {
  key: string;
  label: string;
  icon: string;
  fields: DataField[];
}

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
  ],
  templateUrl: './query-builder.html',
  styleUrl: './query-builder.scss',
})
export class QueryBuilder {
  aiPrompt = signal('');
  selectedArea = signal<DataArea | null>(null);

  dataAreas: DataArea[] = [
    {
      key: 'person',
      label: 'Person',
      icon: 'person',
      fields: [
        { key: 'id',         label: 'ID',           selected: false },
        { key: 'firstName',  label: 'First Name',   selected: false },
        { key: 'lastName',   label: 'Last Name',    selected: false },
        { key: 'email',      label: 'Email',        selected: false },
        { key: 'dob',        label: 'Date of Birth',selected: false },
        { key: 'phone',      label: 'Phone',        selected: false },
      ],
    },
    {
      key: 'absence',
      label: 'Absence',
      icon: 'event_busy',
      fields: [
        { key: 'personId',   label: 'Person',       selected: false },
        { key: 'dayStart',   label: 'Day Start',    selected: false },
        { key: 'dayEnd',     label: 'Day End',      selected: false },
        { key: 'type',       label: 'Type (e.g. Sickness)', selected: false },
        { key: 'notes',      label: 'Notes',        selected: false },
      ],
    },
    {
      key: 'jobs',
      label: 'Jobs',
      icon: 'work',
      fields: [
        { key: 'jobId',      label: 'Job ID',       selected: false },
        { key: 'title',      label: 'Job Title',    selected: false },
        { key: 'department', label: 'Department',   selected: false },
        { key: 'location',   label: 'Location',     selected: false },
        { key: 'startDate',  label: 'Start Date',   selected: false },
        { key: 'salary',     label: 'Salary',       selected: false },
      ],
    },
  ];

  selectArea(area: DataArea) {
    this.selectedArea.set(
      this.selectedArea()?.key === area.key ? null : area
    );
  }

  buildWithAi() {
    console.log('[AI Query Builder] Prompt:', this.aiPrompt());
  }

  get selectedFields(): DataField[] {
    return this.selectedArea()?.fields.filter(f => f.selected) ?? [];
  }
}
