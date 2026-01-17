import {ChangeDetectionStrategy, Component, input, InputSignal, model, ModelSignal} from '@angular/core';
import {MatError, MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {FormValueControl, ValidationError} from '@angular/forms/signals';

@Component({
  selector: 'wt-form-input',
  imports: [
    MatError,
    MatFormField,
    MatLabel,
    MatInput
  ],
  templateUrl: './form-input.html',
  styleUrl: './form-input.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormInput implements FormValueControl<string> {
  // Required for FormValueControl
  public value: ModelSignal<string> = model<string>('');

  // Optional read-only state (provided by the form)
  public disabled: InputSignal<boolean> = input<boolean>(false);
  public readonly: InputSignal<boolean> = input<boolean>(false);
  public invalid: InputSignal<boolean> = input<boolean>(false);
  // public touched: ModelSignal<boolean> = model(false);

  public touched: ModelSignal<boolean> = model(false);
  public errors: InputSignal<readonly ValidationError[]> = input<readonly ValidationError[]>([]);

  // Custom inputs for flexibility (not form-related)
  public label: InputSignal<string> = input<string>('Input Label');
  public placeholder: InputSignal<string> = input<string>('');
  public type: InputSignal<string> = input<string>('text');
}
