import {Link} from "../shared/response/link";

export class TodoLinks {

  self: Link;
  todos: Link;
  createTodo: Link;
  updateTodo: Link;
  deleteTodo: Link;

  constructor(self: Link, createTodo: Link, todos: Link, updateTodo: Link, deleteTodo: Link) {
    this.self = self;
    this.createTodo = createTodo;
    this.todos = todos;
    this.updateTodo = updateTodo;
    this.deleteTodo = deleteTodo;
  }
}
