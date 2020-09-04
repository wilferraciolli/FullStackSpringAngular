import { PersonLinks } from './person-links';
import { PersonMeta } from './person-meta';

export class Person {

  id: string;
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  dateOfBirth?: string;
  genderId?: string;
  maritalStatusId?: string;
  numberOfDependants?: number;
  phoneNumber?: string;
  links: PersonLinks;
  meta: Array<PersonMeta>;

  constructor(id: string,
              userId: string,
              firstName: string,
              lastName: string,
              email: string,
              dateOfBirth: string,
              genderId: string,
              maritalStatusId: string,
              numberOfDependants: number,
              phoneNumber: string,
              links: PersonLinks,
              meta: Array<PersonMeta>) {

    this.id = id;
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.genderId = genderId;
    this.maritalStatusId = maritalStatusId;
    this.numberOfDependants = numberOfDependants;
    this.phoneNumber = phoneNumber;
    this.links = links;
    this.meta = meta;
  }
}
