import {ChangeDetectionStrategy, Component, input, model} from '@angular/core';
import {FormValueControl, ValidationError} from '@angular/forms/signals';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';

@Component({
  selector: 'wt-form-input',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    MatError
  ],
  templateUrl: './form-input.html',
  styleUrl: './form-input.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormInput implements FormValueControl<string> {
// Required for FormValueControl (bound by [field])
  value = model<string>('');

  // Optional writable state
  touched = model<boolean>(false);

  // Optional read-only state from form
  disabled = input<boolean>(false);
  readonly = input<boolean>(false);
  invalid = input<boolean>(false);
  errors = input<readonly ValidationError[]>([]);

  // Custom props (not form-bound)
  label = input<string>('Label');
  placeholder = input<string>('');
  type = input<string>('text');
}
