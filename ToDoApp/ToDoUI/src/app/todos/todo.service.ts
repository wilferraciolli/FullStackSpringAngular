import { Injectable } from '@angular/core';
import { HttpBaseService } from '../shared/response/base-service';
import {ValueViewValue} from '../shared/response/value-viewValue';

@Injectable({
  providedIn: 'root'
})
export class TodoService extends HttpBaseService {

  todoTemplateUrl: string;
  todosUrl: string;

  resolveTodoTypes(values: any): Array<ValueViewValue> {
    // console.log('value extracted is ', values);

    return values
      .map(v => v.typeId.values
        .map(meta => ({ value: meta.id, viewValue: meta.value })))[0];
  }

}
