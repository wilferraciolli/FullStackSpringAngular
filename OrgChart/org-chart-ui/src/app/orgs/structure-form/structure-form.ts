import {ChangeDetectionStrategy, Component, signal} from '@angular/core';
import {email, Field, form, minLength, required} from '@angular/forms/signals';
import {FormInput} from '../../shared/components/form-input/form-input';

@Component({
  selector: 'wt-structure-form',
  imports: [
    FormInput,
    Field
  ],
  templateUrl: './structure-form.html',
  styleUrl: './structure-form.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StructureForm {
  model = signal({
    username: '',
    email: '',
  });

  simpleForm = form(
    this.model,
    (schemaPath) => {
      required(schemaPath.username, {message: 'Username is required'});
      minLength(schemaPath.username, 3, {message: 'Minimum 3 characters'});
      required(schemaPath.email, {message: 'Email is required'});
      email(schemaPath.email, {message: 'Email is invalid'});
    }
  );

  onSubmit() {
    if (this.simpleForm().valid()) {
      this.simpleForm().markAsTouched();
      console.log('Invalid:', this.simpleForm().value());
      // return;
    }
    console.log('Submitted:', this.simpleForm().value());
  }
}
