import { Link } from '../shared/response/link';

export class UserLinks {
  self: Link;
  updateUser: Link;
  deleteUser: Link;
  users: Link;


  constructor(self: Link, updateUser: Link, deleteUser: Link, users: Link) {
    this.self = self;
    this.updateUser = updateUser;
    this.deleteUser = deleteUser;
    this.users = users;
  }
}
