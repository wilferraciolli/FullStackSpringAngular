import { Link } from '../shared/response/link';

export class UserProfileLinks {
  self: Link;
  todos: Link;
  users: Link;
  people: Link;
  person: Link;

  constructor(self: Link, todos: Link, users: Link, people: Link, person: Link) {
    this.self = self;
    this.todos = todos;
    this.users = users;
    this.people = people;
    this.person = person;
  }
}
