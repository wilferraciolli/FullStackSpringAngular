import { Link } from '../shared/response/link';

export class UserLinksCollection {
  self: Link;
  createUser: Link;

  constructor(self: Link, createUser: Link) {
    this.self = self;
    this.createUser = createUser;
  }
}
