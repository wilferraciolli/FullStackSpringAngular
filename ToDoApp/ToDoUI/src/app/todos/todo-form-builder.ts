import {Injectable} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Todo} from './todo';
import {TodoService} from './todo.service';
import * as _ from 'lodash';
import {ValueViewValue} from '../shared/response/value-viewValue';


@Injectable({
  providedIn: 'root'
})
export class TodoFormBuilder {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private todoService: TodoService) {

    this.form = this.formBuilder.group({
      $key: [null],
      typeId: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(2)]],
      description: [''],
      createdDateTime: ['', Validators.required],
      completedDateTime: [''],
      stateId: ['', Validators.required],
      completionStats: [''],
      published: [true],
      links: [null],
      meta: []
    });
  }

  // getter for the form control array
  get typesFormArray(): FormArray {

    return this.form.get('typeId') as FormArray;
  }

  addEmptyTypeFormGroup(): FormGroup {

    return this.formBuilder.group(new ValueViewValue('', ''), Validators.required);
  }

  getFormValue(): Todo {

    const todo = new Todo(
      this.form.controls.$key.value,
      this.form.controls.typeId.value,
      this.form.controls.name.value,
      this.form.controls.description.value,
      this.form.controls.createdDateTime.value,
      this.form.controls.completedDateTime.value,
      this.form.controls.stateId.value,
      this.form.controls.completionStats.value,
      this.form.controls.published.value,
      this.form.controls.links.value,
      this.form.controls.meta.value
    );

    return todo;
  }

  initializeFormGroupWithValues(todo: Todo): void {

    this.form.setValue({
      $key: _.isUndefined(todo) ? '' : todo.id,
      typeId: _.isUndefined(todo) ? '' : todo.typeId,
      name: _.isUndefined(todo) ? '' : todo.name,
      description: _.isUndefined(todo) ? '' : todo.description,
      createdDateTime: _.isUndefined(todo) ? '' : todo.createdDateTime,
      completedDateTime: _.isUndefined(todo) ? '' : todo.completedDateTime,
      stateId: _.isUndefined(todo) ? '' : todo.stateId,
      completionStats: _.isUndefined(todo) ? '' : todo.completionStats,
      published: _.isUndefined(todo) ? '' : todo.published,
      links: '',
      meta: '',
    });
  }

  populateForm(todo: Todo): void {

    this.form.setValue(
      {
        $key: todo.id,
        typeId: todo.typeId,
        name: todo.name,
        description: todo.description,
        createdDateTime: todo.createdDateTime,
        completedDateTime: todo.completedDateTime,
        stateId: todo.stateId,
        completionStats: todo.completionStats,
        published: todo.published,
        links: {
          self: todo.links.self,
          createTodo: todo.links.createTodo,
          updateTodo: todo.links.updateTodo,
          deleteTodo: todo.links.deleteTodo
        },
        meta: ''
      }
    );
  }


  private transformTodoTypesToTodoTypeViewValue(todo: Todo): Array<ValueViewValue> {

    const todoTypesViewValues = this.todoService.resolveTodoTypes(Object.values(todo.meta)
      .filter(g => g.hasOwnProperty('typeId')))
      .filter(type => todo.typeId.includes(type.value));

    this.addTypeFormGroupToEachTodoType(todoTypesViewValues.length);

    return todoTypesViewValues;
  }

  private addTypeFormGroupToEachTodoType(size: number): void {

    for (let i = 0; i < size; i++) {
      this.typesFormArray.push(this.addEmptyTypeFormGroup());
    }
  }
}
