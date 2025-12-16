import {ChangeDetectionStrategy, Component, input, InputSignal, model, ModelSignal} from '@angular/core';
import {MatError, MatFormField, MatLabel} from "@angular/material/input";
import {FormValueControl, ValidationError} from '@angular/forms/signals';

@Component({
  selector: 'wt-form-input',
  imports: [
    MatError,
    MatFormField,
    MatLabel
  ],
  templateUrl: './form-input.html',
  styleUrl: './form-input.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormInput implements FormValueControl<string> {
  // Required for FormValueControl
  public value: ModelSignal<string> = model<string>('');

  // Optional read-only state (provided by the form)
  disabled: InputSignal<boolean> = input<boolean>(false);
  readonly: InputSignal<boolean> = input<boolean>(false);
  invalid: InputSignal<boolean> = input<boolean>(false);
  errors: InputSignal<readonly ValidationError[]> = input<readonly ValidationError[]>([]);

  // Custom inputs for flexibility (not form-related)
  label: InputSignal<string> = input<string>('Input Label');
  placeholder: InputSignal<string> = input<string>('');
  type: InputSignal<string> = input<string>('text');
}
