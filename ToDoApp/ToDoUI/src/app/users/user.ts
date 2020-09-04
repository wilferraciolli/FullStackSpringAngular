import { UserLinks } from './user-links';
import * as _ from 'lodash';
import { UserMeta } from './user-meta';

export class User {

  id: string;
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  dateOfBirth?: string;
  active: boolean;
  roleIds: Array<string>;
  links: UserLinks;
  meta: Array<UserMeta>;

  constructor(id: string,
              firstName: string,
              lastName: string,
              username: string,
              password: string,
              dateOfBirth: string,
              active: boolean,
              roleIds: Array<string>,
              links: UserLinks,
              meta: Array<UserMeta>) {

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.active = active;
    this.roleIds = this.resolveUserRoleIds(roleIds);
    this.links = links;
    this.meta = meta;
  }

  /**
   * Resolves the roleIds. If roleIds are null, instantiate, otherwise return its original value.
   * @param roles The roleIds
   */
  private resolveUserRoleIds(roleIds: Array<string>): Array<string> {

    return _.isNull(roleIds) ? [] : roleIds;
  }
}
