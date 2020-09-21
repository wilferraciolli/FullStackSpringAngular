import {Link} from "../shared/response/link";

export class TodoLinksCollection {

  self: Link;
  createTodo: Link;

  constructor(self: Link, createTodo: Link) {
    this.self = self;
    this.createTodo = createTodo;
  }
}
