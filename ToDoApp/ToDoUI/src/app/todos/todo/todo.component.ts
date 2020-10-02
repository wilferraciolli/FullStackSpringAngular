import {Component, Inject, OnInit, Optional} from '@angular/core';
import {Todo} from '../todo';
import {TodoService} from '../todo.service';
import {TodoFormBuilder} from '../todo-form-builder';
import {TodoAdapter} from '../todo.adapter';
import {NotificationService} from '../../shared/notification.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {LinksService} from '../../_services/links-service';
import {TodoPayload} from '../todo-payload';
import {ValueViewValue} from '../../shared/response/value-viewValue';
import {ActivatedRoute} from '@angular/router';
import {TodoMeta} from '../todo-meta';
import {Link} from '../../shared/response/link';
import {TodoLinks} from '../todo-links';
import {ProgressBarMode} from '@angular/material/progress-bar';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss']
})
export class TodoComponent implements OnInit {

  // completion status progress bar
  // mode: ProgressBarMode = 'determinate';
  // value = 50;
  // bufferValue = 75;

  selfLink: Link;
  todo: Todo;
  todoMetadata: TodoMeta;
  todoLinks: TodoLinks;

  todoTypes: Array<ValueViewValue>;
  todoStates: Array<ValueViewValue>;

  constructor(
    private todoService: TodoService,
    private activatedRoute: ActivatedRoute,
    public todoFormBuilder: TodoFormBuilder,
    private adapter: TodoAdapter,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<TodoComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    if (data) {
      this.selfLink = data.todoLink;
    } else {
      console.warn('no meta was passed');
    }
  }

  ngOnInit(): void {
    this.loadTodo(this.selfLink.href);
  }

  loadTodo(url: string): void {
    this.todoService.getByUrl(url)
      .then(response => {
        const data = response['_data'];
        const links = response['_links'];
        const meta = response['_meta'];

        this.todo = this.adapter.adapt(data, links, meta);
        this.todoMetadata = meta;
        this.todoLinks = links;

        this.todoTypes = this.getAvailableTodoTypes(Object.values(this.todo.meta).filter(g => g.hasOwnProperty('typeId')));
        this.todoStates = this.getAvailableTodoStatuses(Object.values(this.todo.meta).filter(g => g.hasOwnProperty('stateId')));

        this.todoFormBuilder.initializeFormGroupWithValues(this.todo);
      });
  }

  /**
   * Clear out form and re initialize it
   */
  onClear(): void {
    this.todoFormBuilder.form.reset();
    this.notificationService.success('Form cleared successfully');
  }

  onSubmit(): void {
    if (this.todoFormBuilder.form.valid) {

      if (this.todoFormBuilder.form.value.$key) {
        this.update();
      } else {
        this.create();
      }

      this.todoFormBuilder.form.reset();
      this.todoService.reloadCurrentRoute();
      this.onClose();
    }
  }

  create(): void {
    console.log('Adding');

    this.todoService.add('http://localhost:5001/api/todos', new TodoPayload(this.todoFormBuilder.getFormValue()))
      .subscribe(data => {
          console.log('Success', data);
          this.notificationService.success('Todo created successfully');
        },
        error => {
          console.log('Error', error);
          this.notificationService.error('Todo could not be created');
        });
  }

  update(): void {
    console.log('updating');
    // const updateUrl = this.todoFormBuilder.form.value.links.updateTodo.href;

    this.todoService.update(this.todoLinks.updateTodo.href, new TodoPayload(this.todoFormBuilder.getFormValue()))
      .subscribe(data => {
          console.log('Success', data);
          this.notificationService.success('Todo updated successfully');
        },
        error => {
          console.log('Error', error);
          this.notificationService.error('Todo could not be updated');
        });
  }

  /**
   * Method to be called once the add todo dialog is closed.
   */
  onClose(): void {
    this.todoFormBuilder.form.reset();
    // this.todoFormBuilder.initializeFormGroup();
    this.dialogRef.close();
  }

  progressLabel(value: number){

      return value + '%';
  }

  private getAvailableTodoTypes(values: any): Array<ValueViewValue> {

    return values
      .map(v => v.typeId.values
        .map(meta => ({value: meta.id, viewValue: meta.value})))[0];
  }

  private getAvailableTodoStatuses(values: any): Array<ValueViewValue> {

    return values
      .map(v => v.stateId.values
        .map(meta => ({value: meta.id, viewValue: meta.value})))[0];
  }
}
