import {ChangeDetectionStrategy, Component, signal} from '@angular/core';
import {form,Field, minLength, required} from '@angular/forms/signals';
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
  registrationModel = signal({
    username: '',
    email: '',
  });

  registrationForm = form(
    this.registrationModel,
    (schemaPath) => {
      required(schemaPath.username, {message: 'Username is required'});
      minLength(schemaPath.username, 3, {message: 'Username must be at least 3 characters'});
      required(schemaPath.email, {message: 'Email is required'});
    }
  );

  onSubmit() {
    if (this.registrationForm().invalid()) {
      this.registrationForm().markAsTouched();
      return;
    }
    console.log('Form submitted:', this.registrationForm().value());
    // Handle submission logic here
  }
}
