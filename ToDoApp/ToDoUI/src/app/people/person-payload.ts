import { Person } from './person';

export class PersonPayload {

  person: Person;

  constructor(person: Person) {
    this.person = person;
  }
}
