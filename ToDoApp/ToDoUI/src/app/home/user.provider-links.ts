import { Link } from '../shared/response/link';

export class UserProfileLinks {
  self: Link;
  todos: Link;
  completedTodos: Link;
  activeTodos: Link;
  newTodos: Link;
  users: Link;
  people: Link;
  person: Link;

  constructor(self: Link, todos: Link, completedTodos: Link, activeTodos: Link, newTodos: Link, users: Link, people: Link, person: Link) {
    this.self = self;
    this.todos = todos;
    this.completedTodos = completedTodos;
    this.activeTodos = activeTodos;
    this.newTodos = newTodos;
    this.users = users;
    this.people = people;
    this.person = person;
  }
}
