import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  // config for the pop up
  config: MatSnackBarConfig = {
    duration: 2000,
    // horizontalPosition: 'right',
    // verticalPosition: 'top'
  }

  errorConfig: MatSnackBarConfig = {
    duration: 4000,
    horizontalPosition: 'right',
    verticalPosition: 'top'
  }

  constructor(public snackBar: MatSnackBar) {
  }

  success(message) {
    // set success message
    this.config['panelClass'] = ['notification', 'success'];
    this.snackBar.open(message, '', this.config);
  }

  warn(message) {
    // set warn message
    this.config['panelClass'] = ['notification', 'warn'];
    this.snackBar.open(message, '', this.config);
  }

  error(message) {
    // set error message
    this.config['panelClass'] = ['notification', 'error'];
    this.snackBar.open(message, '', this.errorConfig);
  }
}
