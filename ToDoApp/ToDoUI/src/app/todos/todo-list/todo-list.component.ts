import {Component, OnInit} from '@angular/core';
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
import {TodoLinksCollection} from '../todo-links-collection';
import {LinksService} from '../../_services/links-service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  searchKey: string;
  todos: Array<Todo>;
  canCreate: boolean;

  // todoTemplateLink: Link;
  collectionMeta: TodoMeta;
  collectionLinks: TodoLinksCollection;

  isExtraSmall: Observable<BreakpointState> = this.breakpointObserver.observe(Breakpoints.XSmall);

  constructor(private breakpointObserver: BreakpointObserver,
              private todoService: TodoService,
              private todoFormBuilder: TodoFormBuilder,
              private adapter: TodoAdapter,
              private notificationService: NotificationService,
              private dialog: MatDialog,
              private router: Router,
              private dialogService: DialogService,
              private activatedRoute: ActivatedRoute,
              private linksService: LinksService) {
  }

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

        this.todos = this.convertResponse(response._embedded.collection);
        this.collectionMeta = this.resolveCollectionMeta(response._meta);
        this.collectionLinks = this.resolveCollectinLinks(response._links);

        this.canCreate = this.linksService.hasLink(this.collectionLinks.createTodo);
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
    const dialogConfig = this.dialog.open(TodoComponent, {
      width: '50%',
      height: '50%',
      maxWidth: '100vw',
      maxHeight: '100vh',
      data: {todoLink: this.collectionLinks.createTodo}
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

  onEdit(row: Todo): void {

   // this.getSingleTodo(new Link(row.links.self.href)).then((data) => {
    //  this.todoFormBuilder.populateForm(this.adapter.adapt(data._data, data._links, data._meta));

      const dialogConfig = this.dialog.open(TodoComponent, {
        width: '50%',
        height: '50%',
        maxWidth: '100vw',
        maxHeight: '100vh',
        data: { todoLink: new Link(row.links.self.href) }
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
   // });
  }

  getSingleTodo(url: string): Promise<any> {

    return this.todoService.getByUrl(url);
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

  private resolveCollectionMeta(collectionMeta: any): TodoMeta {

    return collectionMeta;
  }

  private resolveCollectinLinks(collectionLinks: any): TodoLinksCollection {

    return collectionLinks;
  }
}
