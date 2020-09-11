import { Component, OnInit } from '@angular/core';
import {Link} from '../../shared/response/link';
import {Observable} from 'rxjs';
import {BreakpointObserver, Breakpoints, BreakpointState} from '@angular/cdk/layout';
import {NotificationService} from '../../shared/notification.service';
import {MatDialog} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogService} from '../../shared/dialog.service';
import * as _ from 'lodash';
import {Todo} from '../todo';
import {TodoMeta} from '../todo-meta';
import {TodoService} from '../todo.service';
import {TodoFormBuilder} from '../todo-form-builder';
import {TodoAdapter} from '../todo.adapter';
import {TodoResponse} from '../todo-response';
import {TodoComponent} from '../todo/todo.component';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  searchKey: string;
  todos: Array<Todo>;
  todo: Todo;

  todoTemplateLink: Link;
  todoCreateAccess = true;
  collectionMeta: TodoMeta;

  isExtraSmall: Observable<BreakpointState> = this.breakpointObserver.observe(Breakpoints.XSmall);

  constructor(private breakpointObserver: BreakpointObserver,
              private todoService: TodoService,
              private todoFormBuilder: TodoFormBuilder,
              private adapter: TodoAdapter,
              private notificationService: NotificationService,
              private dialog: MatDialog,
              private router: Router,
              private dialogService: DialogService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    // send the request to home if no link is present
    if (_.isUndefined(history.state.todosLink)
      && _.isUndefined(this.todoService.todosUrl)) {

      this.router.navigate(['/home']);
    } else {
      if (_.isUndefined(this.todoService.todosUrl)) {
        this.todoService.todosUrl = history.state.todosLink.href;
      }
    }

    this.getAll(this.todoService.todosUrl);
  }


  getAll(url: string): void {
    this.todoService.getAll<TodoResponse>(url)
      .subscribe((response: TodoResponse) => {
        // const embedded = response['_embedded'];
        const embedded = response._embedded;
        const metaLinks = response._links;
        const meta = response._meta;

        this.todoTemplateLink = metaLinks.createTodo;

        // todos.
        const collectionBody: any[] = embedded.collection;
        this.todos = this.convertResponse(collectionBody);
        // console.log(this.todo);
      });
  }

  convertResponse(collectionBody: any[]): Array<Todo> {

    return collectionBody.map(item =>
      this.adapter.adapt(item._data, item._links, item._meta));
  }

  onSearchClear(): void {
    this.searchKey = '';
    this.applyFilter();
  }

  applyFilter(): void {
    // this.todos.filter = this.searchKey.trim().toLowerCase();
  }

  create(): void {
    // get the template and populate with default values
    this.todo = this.getTemplate(this.todoTemplateLink.href);
    this.todoFormBuilder.initializeFormGroupWithTemplateValues(this.todo);

    // set default values
    console.log('The form value is ', this.todoFormBuilder.getFormValue());
    this.todoFormBuilder.initializeFormGroup();

    const dialogConfig = this.dialog.open(TodoComponent, {
      width: '50%',
      height: '50%',
      maxWidth: '100vw',
      maxHeight: '100vh',
      data: {todoMeta: this.collectionMeta }
    });

    // subscribe to screen size
    const smallDialogSubscription = this.isExtraSmall.subscribe(result => {
      if (result.matches) {
        dialogConfig.updateSize('100%', '100%');
      } else {
        dialogConfig.updateSize('75%', '75%');
      }
    });
    dialogConfig.afterClosed().subscribe(result => {
      smallDialogSubscription.unsubscribe();
    });
  }

  /**
   * Method used to pre-populate the form before creation.
   * @param url the url
   */
  getTemplate(url: string): Todo {
    let todoTemplate;
    this.todoService.getTemplate(url)
      .subscribe(response => {
        const data = response['_data'];
        const links = response['_links'];
        const meta = response['meta'];

        todoTemplate = this.adapter.adapt(data, links, meta);
      });

    return todoTemplate;
  }

  onEdit(row: Todo): void {

    this.getSingleTodo(row.links.self.href).then((data) => {
      this.todoFormBuilder.populateForm(this.adapter.adapt(data._data, data._links, data._meta));

      const dialogConfig = this.dialog.open(TodoComponent, {
        width: '50%',
        height: '50%',
        maxWidth: '100vw',
        maxHeight: '100vh',
        data: {todoMeta: data._meta}
      });

      // subscribe to screen size
      const smallDialogSubscription = this.isExtraSmall.subscribe(result => {
        if (result.matches) {
          dialogConfig.updateSize('100%', '100%');
        } else {
          dialogConfig.updateSize('75%', '75%');
        }
      });
      dialogConfig.afterClosed().subscribe(result => {
        smallDialogSubscription.unsubscribe();
      });
    });
  }

  getSingleTodo(url: string): Promise<any> {

    return this.todoService.getById(url);
  }

  delete(url: string): void {

    this.dialogService.openConfirmDialog('Are you sure you want to delete this todo?')
      .afterClosed().subscribe(res => {
      if (res) {
        this.todoService.delete(url)
          .subscribe(data => {
              console.log('Success', data);
              this.notificationService.warn('Todo deleted successfully');
              this.todoService.reloadCurrentRoute(); // not working
            },
            error => {
              console.log('Error', error);
              this.notificationService.error('Todo could not be deleted');
            });
      }
    });

    // refresh todos
    this.getAll(this.todoService.todosUrl);
  }
}
