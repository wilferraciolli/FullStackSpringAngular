export interface UserProfileResponse {

  userId: string;
  firstName: string;
  lastName: string;
  _links: {
    users: {
      href: string
    },
    people: {
      href: string
    },
    providers: {
      href: string
    }
  };
}
