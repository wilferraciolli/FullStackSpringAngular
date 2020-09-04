import { Injectable } from '@angular/core';
import { Adapter } from '../shared/response/adapter';
import { User } from './user';
import { UserLinks } from './user-links';

@Injectable({
  providedIn: 'root'
})
export class UserAdapter implements Adapter<User> {

  adapt(data: any, links: any, meta?: any): User {

    return new User(data.user.id,
      data.user.firstName,
      data.user.lastName,
      data.user.username,
      data.user.password,
      data.user.dateOfBirth,
      data.user.active,
      data.user.roleIds,
      new UserLinks(links.self,
        links.updateUser,
        links.deleteUser,
        links.users),
      meta);
  }

}
