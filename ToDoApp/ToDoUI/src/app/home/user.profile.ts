import { UserProfileLinks } from './user.provider-links';

export class UserProfile {

  userId: string;
  personId: string;
  firstName: string;
  lastName: string;
  links: UserProfileLinks;

  constructor(data: any) {
    this.userId = data.userProfile.userId;
    this.personId = data.userProfile.personId;
    this.firstName = data.userProfile.firstName;
    this.lastName = data.userProfile.lastName;
    this.links = data.userProfile._links;
  }
}
