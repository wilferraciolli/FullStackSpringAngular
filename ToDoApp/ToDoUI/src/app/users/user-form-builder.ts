import { Injectable } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from './user';
import { ValueViewValue } from '../shared/response/value-viewValue';
import { UserServiceService } from './user-service.service';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class UserFormBuilder {

  form: FormGroup;
  defaultUserRoleId = 'ROLE_USER';
  defaultUserRole = 'User';

  constructor(private formBuilder: FormBuilder,
              private userService: UserServiceService) {

    this.form = this.formBuilder.group({
      $key: [null],
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: [''],
      active: [''],
      roles: this.formBuilder.array([this.addEmptyRoleFormGroup()]),
      links: [null],
      meta: []
    });
  }

  // getter for the form control array
  get rolesFormArray(): FormArray {

    return this.form.get('roles') as FormArray;
  }

  addEmptyRoleFormGroup(): FormGroup {

    return this.formBuilder.group(new ValueViewValue('', ''), Validators.required);
  }

  getFormValue(): User {

    const user = new User(
      this.form.controls.$key.value,
      this.form.controls.firstName.value,
      this.form.controls.lastName.value,
      this.form.controls.username.value,
      this.form.controls.password.value,
      this.form.controls.dateOfBirth.value,
      this.form.controls.active.value,
      this.getRoleIds(this.form.controls.roles.value),
      this.form.controls.links.value,
      this.form.controls.meta.value
    );

    return user;
  }

  /**
   * Initialize the form with default values.
   */
  initializeFormGroup() {

    this.resetArrayFormValues();

    this.form.setValue({
      $key: null,
      firstName: 'FirstName',
      lastName: 'LastName',
      username: 'someone@wiltech.com',
      password: 'password1',
      dateOfBirth: '1985-01-01',
      active: true,
      roles: Array.of(new ValueViewValue(this.defaultUserRoleId, this.defaultUserRole)),
      links: '',
      meta: ''
    });
  }

  initializeFormGroupWithTemplateValues(user: User) {
    console.log('the value of user is', user);

    this.form.setValue(
      {
        $key: null,
        firstName: _.isUndefined(user) ? '' : user.firstName,
        lastName:  _.isUndefined(user) ? '' : user.lastName,
        username:  _.isUndefined(user) ? '' : user.username,
        password:  _.isUndefined(user) ? '' : user.password,
        dateOfBirth:  _.isUndefined(user) ? '' : user.dateOfBirth,
        active: true,
        roles: Array.of(new ValueViewValue(this.defaultUserRoleId, this.defaultUserRole)),
        links: '',
        meta: ''
      }
    );
  }

  populateForm(user: User) {
    // console.log('the value of user is ', user);

    this.form.setValue(
      {
        $key: user.id,
        firstName: user.firstName,
        lastName: user.lastName,
        username: user.username,
        password: user.password,
        dateOfBirth: user.dateOfBirth,
        active: user.active,
        roles: this.transformUserRRolesToRolesViewValue(user),
        links: {
          self: user.links.self,
          updateUser: user.links.updateUser,
          deleteUser: user.links.deleteUser,
          users: user.links.users
        },
        meta: user.meta
      }
    );
  }

  addRoleFormGroup() {
    this.rolesFormArray.push(this.addEmptyRoleFormGroup());
  }

  deleteRoleFormGroup(index: number) {
    this.rolesFormArray.removeAt(index);
  }

  private getRoleIds(roles: Array<ValueViewValue>): Array<string> {
    const roleIds = new Array();
    roles.forEach(role => {
      roleIds.push(role.value);
    });

    return roleIds;
  }

  private resetArrayFormValues(): void {

    // reset the array form values
    const rolesArrayLength = this.rolesFormArray.length;
    if (rolesArrayLength > 1) {
      for (let i = rolesArrayLength; i > 1; i--) {
        this.rolesFormArray.removeAt(i - 1);
      }
    }
  }

  private transformUserRRolesToRolesViewValue(user: User): Array<ValueViewValue> {

    const UserRolesViewValues = this.userService.resolveUserRoles(Object.values(user.meta)
      .filter(g => g.hasOwnProperty('roleIds')))
      .filter(role => user.roleIds.includes(role.value));

    this.addRolesFormGroupTRoEachUserRole(UserRolesViewValues.length);

    return UserRolesViewValues;
  }

  // Add the same number of user roles to the rolesFormGroup
  private addRolesFormGroupTRoEachUserRole(userRolesSize: number): void {
    // remove the default one
    this.rolesFormArray.removeAt(0);

    for (let i = 0; i < userRolesSize; i++) {
      this.rolesFormArray.push(this.addEmptyRoleFormGroup());
    }
  }

}
