import {Todo} from "./todo";

/**
 * Class used to wrap the payload before sending to the server.
 */
export class TodoPayload {

  todo: Todo;

  constructor(todo: Todo) {
    this.todo = todo;
  }
}
