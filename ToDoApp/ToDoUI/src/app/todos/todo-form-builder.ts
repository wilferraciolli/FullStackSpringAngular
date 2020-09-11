import {Injectable} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Todo} from './todo';
import {TodoService} from './todo.service';


@Injectable({
  providedIn: 'root'
})
export class TodoFormBuilder {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private providerService: TodoService) {

    this.form = this.formBuilder.group({
      $key: [null],
      typeId: ['', Validators.required],
      name: [''],
      description: ['', Validators.required],
      createdDateTime: [''],
      completedDateTime: [''],
      stateId: [''],
      completionStats: [''],
      enabled: [true],
      links: [null],
      meta: []
    });
  }

  getFormValue(): Todo {

    const provider = new Todo(
      this.form.controls.$key.value,
      this.form.controls.typeId.value,
      this.form.controls.name.value,
      this.form.controls.description.value,
      this.form.controls.createdDateTime.value,
      this.form.controls.completedDateTime.value,
      this.form.controls.stateId.value,
      this.form.controls.completionStats.value,
      this.form.controls.enabled.value,
      this.form.controls.links.value,
      this.form.controls.meta.value
    );

    return provider;
  }

  /**
   * Initialize the form with default values.
   */
  initializeFormGroup(): void {
    this.form.setValue({
      $key: null,
      typeId: '',
      name: '',
      description: '',
      createdDateTime: '',
      completedDateTime: '',
      stateId: '',
      completionStats: '',
      enabled: true,
      links: '',
      meta: ''
    });
  }

  initializeFormGroupWithTemplateValues(provider: Todo): void {
    this.form.setValue({
      $key: null,
      typeId: '',
      name: '',
      description: '',
      createdDateTime: '',
      completedDateTime: '',
      stateId: '',
      completionStats: '',
      enabled: true,
      links: '',
      meta: ''
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
        enabled: todo.enabled,
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
}
