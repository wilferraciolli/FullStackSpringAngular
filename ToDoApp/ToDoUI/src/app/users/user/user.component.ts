import { Component, Inject, OnInit, Optional } from '@angular/core';
import { UserServiceService } from '../user-service.service';
import { NotificationService } from '../../shared/notification.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserAdapter } from '../user-adapter';
import { UserPayload } from '../user-payload';
import { UserFormBuilder } from '../user-form-builder';
import { ValueViewValue } from '../../shared/response/value-viewValue';
import { UserMeta } from '../user-meta';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  hide = true;
  userCollectionMeta: UserMeta;
  availableRoles: Array<ValueViewValue>;

  constructor(private userService: UserServiceService,
              public userFormBuilder: UserFormBuilder,
              private adapter: UserAdapter,
              private notificationService: NotificationService,
              public dialogRef: MatDialogRef<UserComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {

    // get the metadata passed from the caller component
    if (data) {
      this.userCollectionMeta = data.userMeta;
      this.availableRoles = this.getAvailableUserRoles(Object.values(this.userCollectionMeta)
        .filter(g => g.hasOwnProperty('roleIds')));
    } else {
      console.warn('no meta was passed');
    }
  }

  ngOnInit() {
  }

  /**
   * Clear out form and re initialize it
   */
  onClear() {
    this.userFormBuilder.form.reset();
    this.userFormBuilder.initializeFormGroup();
    this.notificationService.success('Form cleared successfully');
  }

  onSubmit() {
    console.log('the value of the form is ', this.userFormBuilder.getFormValue());
    if (this.userFormBuilder.form.valid) {

      if (this.userFormBuilder.form.value.$key) {
        this.update();
      } else {
        this.create();
      }

      this.userFormBuilder.form.reset();
      this.userFormBuilder.initializeFormGroup();
      this.userService.reloadCurrentRoute();
      this.onClose();
    }
  }

  create() {
    console.log('Adding');

    this.userService.add('http://localhost:5001/api/users', new UserPayload(this.userFormBuilder.getFormValue()))
      .subscribe(data => {
          console.log('Success', data);
          this.notificationService.success('User created successfully');
        },
        error => {
          console.log('Error', error);
          this.notificationService.error('User could not be created');
        });
  }

  update() {
    console.log('updating');
    const updateUrl = this.userFormBuilder.form.value.links.updateUser.href;

    this.userService.update(updateUrl, new UserPayload(this.userFormBuilder.getFormValue()))
      .subscribe(data => {
          console.log('Success', data);
          this.notificationService.success('User updated successfully');
        },
        error => {
          console.log('Error', error);
          this.notificationService.error('User could not be updated');
        });
  }

  /**
   * Method to be called once the add dialog is closed.
   */
  onClose() {
    this.userFormBuilder.form.reset();
    this.userFormBuilder.initializeFormGroup();
    this.dialogRef.close();
  }

  private getAvailableUserRoles(values: any):
    Array<ValueViewValue> {
    // console.log('value extracted is ', values);

    return values
      .map(v => v.roleIds.values
        .map(meta => ({ value: meta.id, viewValue: meta.value })))[0];
  }
}
