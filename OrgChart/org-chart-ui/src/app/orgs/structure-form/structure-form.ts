import {ChangeDetectionStrategy, Component, computed, Signal, signal, WritableSignal} from '@angular/core';
import {FieldTree, form, FormField, minLength, required} from '@angular/forms/signals';
import {FormInput} from '../../shared/components/form-input/form-input';
import {JsonPipe} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {StructureFormBody} from './structure.form';
import {OrgNodeType} from '../org-node-type.constant';

@Component({
  selector: 'wt-structure-form',
  imports: [
    FormInput,
    FormField,
    JsonPipe,
    MatButton
  ],
  templateUrl: './structure-form.html',
  styleUrl: './structure-form.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StructureForm {
  protected registrationModel: WritableSignal<StructureFormBody> = signal<StructureFormBody>({
    id: null,
    name: '',
    structureType: OrgNodeType.JOB,
    startDate: '',
    endDate: '',
  });

  protected submitEnabled: Signal<boolean> = computed(() => {
    return this.registrationForm()?.invalid()
      || this.registrationForm()?.submitting();
  });

  protected registrationForm: FieldTree<StructureFormBody> = form(
    this.registrationModel, (schemaPath) => [
      required(schemaPath.name, {message: 'Name is required'}),
      minLength(schemaPath.name, 3, {message: 'Name must be at least 3 characters'}),
      required(schemaPath.structureType, {message: 'Type is required'}),
      required(schemaPath.startDate, {message: 'Start Date is required'})
    ]
  );

  protected onSubmit(): void {
    if (this.registrationForm().invalid()) {
      this.registrationForm().markAsTouched();

      return;
    }
    console.log('Form submitted:', this.registrationForm().value());
    // Handle submission logic here
  }
}
