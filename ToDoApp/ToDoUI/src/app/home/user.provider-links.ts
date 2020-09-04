import { Link } from '../shared/response/link';

export class UserProfileLinks {
  self: Link;
  providers: Link;
  users: Link;
  people: Link;
  person: Link;

  constructor(self: Link, providers: Link, users: Link, people: Link, person: Link) {
    this.self = self;
    this.providers = providers;
    this.users = users;
    this.people = people;
    this.person = person;
  }
}
