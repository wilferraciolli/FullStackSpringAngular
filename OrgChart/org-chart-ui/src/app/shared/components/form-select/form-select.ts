import {ChangeDetectionStrategy, Component, input, InputSignal, model, ModelSignal} from '@angular/core';
import {FormValueControl, ValidationError} from '@angular/forms/signals';
import {MatError, MatFormField, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {FormSelectOption} from './form-select-option';

@Component({
  selector: 'wt-form-select',
  imports: [
    MatError,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption
  ],
  templateUrl: './form-select.html',
  styleUrl: './form-select.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormSelect implements FormValueControl<string> {
  // Required for FormValueControl
  public value: ModelSignal<string> = model<string>('');

  public options: InputSignal<Array<FormSelectOption>> = input<Array<FormSelectOption>>([]);

  // Optional read-only state (provided by the form)
  public disabled: InputSignal<boolean> = input<boolean>(false);
  public readonly: InputSignal<boolean> = input<boolean>(false);
  public invalid: InputSignal<boolean> = input<boolean>(false);

  public touched: ModelSignal<boolean> = model(false);
  public errors: InputSignal<readonly ValidationError[]> = input<readonly ValidationError[]>([]);

  // Custom inputs for flexibility (not form-related)
  public label: InputSignal<string> = input<string>('Select Label');
  public addInitialBlankOption: InputSignal<boolean> = input<boolean>(false);
  public type: InputSignal<string> = input<string>('select');

}
