import { Injectable } from '@angular/core';
import { HttpBaseService } from '../shared/response/base-service';
import { ValueViewValue } from '../shared/response/value-viewValue';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService extends HttpBaseService {

  userTemplateUrl: string;
  usersUrl: string;

  resolveUserRoles(values: any): Array<ValueViewValue> {
    // console.log('value extracted is ', values);

    return values
      .map(v => v.roleIds.values
        .map(meta => ({ value: meta.id, viewValue: meta.value })))[0];
  }
}
