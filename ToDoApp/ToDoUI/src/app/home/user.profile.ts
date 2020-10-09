import { UserProfileLinks } from './user.provider-links';

export class UserProfile {

  private _userId: string;
  private _personId: string;
  private _firstName: string;
  private _lastName: string;
  private _links: UserProfileLinks;

  constructor(data: any) {
    this._userId = data.userProfile._userId;
    this._personId = data.userProfile._personId;
    this._firstName = data.userProfile._firstName;
    this._lastName = data.userProfile._lastName;
    this._links = data.userProfile._links;
  }

  get userId(): string {
    return this._userId;
  }

  get personId(): string {
    return this._personId;
  }

  get firstName(): string {
    return this._firstName;
  }

  get lastName(): string {
    return this._lastName;
  }

  get links(): UserProfileLinks {
    return this._links;
  }
}
