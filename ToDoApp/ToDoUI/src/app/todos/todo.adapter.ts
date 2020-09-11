// class to implement the model adapter pattern
import {Injectable} from '@angular/core';
import {Adapter} from '../shared/response/adapter';
import {Todo} from './todo';
import {TodoLinks} from './todo-links';

@Injectable({
  providedIn: 'root'
})
export class TodoAdapter implements Adapter<Todo> {

  adapt(data: any, links: any, meta: any): Todo {

    return new Todo(data.todo.id,
      data.todo.typeId,
      data.todo.name,
      data.todo.description,
      data.todo.createdDateTime,
      data.todo.completedDateTime,
      data.todo.stateId,
      data.todo.completionStats,
      data.todo.enabled,
      new TodoLinks(links.self,
        links.createTodo,
        links.todos,
        links.updateTodo,
        links.deleteTodo),
      meta);
  }
}
