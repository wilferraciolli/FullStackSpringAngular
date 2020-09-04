import { User } from './user';

/**
 * Class used to wrap the payload before sending to the server.
 */
export class UserPayload {

  user: User;

  constructor(user: User) {
    this.user = user;
  }
}
