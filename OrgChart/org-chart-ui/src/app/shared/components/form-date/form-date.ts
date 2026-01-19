import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  InputSignal,
  model,
  ModelSignal,
  Signal
} from '@angular/core';
import {MatError, MatFormField, MatInput, MatLabel, MatSuffix} from '@angular/material/input';
import {FormValueControl, ValidationError} from '@angular/forms/signals';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, NativeDateAdapter} from '@angular/material/core';
import {MY_DATE_FORMATS} from './form-date-format.constant';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';


@Component({
  selector: 'wt-form-date',
  imports: [
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatSuffix,
    MatDatepicker
  ],
  providers: [
    {provide: DateAdapter, useClass: NativeDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS},
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'}
  ],
  templateUrl: './form-date.html',
  styleUrl: './form-date.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormDate implements FormValueControl<string | null> {
  // Required for FormValueControl ISO YYYY-MM-DD
  public value: ModelSignal<string | null> = model<string | null>(null);

  // Optional read-only state (provided by the form)
  public disabled: InputSignal<boolean> = input<boolean>(false);
  public invalid: InputSignal<boolean> = input<boolean>(false);
  public touched: ModelSignal<boolean> = model(false);
  public errors: InputSignal<readonly ValidationError[]> = input<readonly ValidationError[]>([]);

  // Custom inputs for flexibility (not form-related)
  public label: InputSignal<string> = input<string>('Select Date');
  public placeholder: InputSignal<string> = input<string>('YYYY-MM-DD');

  protected businessDayObject: Signal<Date | null> = computed(() => {
    const value: string | null = this.value();

    return value
      ? new Date(value + 'T00:00:00')
      : null;
  });

  protected onDateChange(newDate: Date | null): void {
    if (!newDate) {
      this.value.set(null);
      return;
    }

    // format to YYY-MM-DD to avoid timezone issues
    const year: number = newDate.getFullYear();
    const month: string = String(newDate.getMonth() + 1).padStart(2, '0');
    const day: string = String(newDate.getDate()).padStart(2, '0');

    this.value.set(`${year}-${month}-${day}`);
  }
}
