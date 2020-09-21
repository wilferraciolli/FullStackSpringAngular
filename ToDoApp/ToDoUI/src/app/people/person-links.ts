import { Link } from '../shared/response/link';

export class PersonLinks {
  self: Link;
  updatePerson: Link;
  deletePerson: Link;
  people: Link;

  constructor(self: Link, updatePerson: Link, deletePerson: Link, people: Link) {
    this.self = self;
    this.updatePerson = updatePerson;
    this.deletePerson = deletePerson;
    this.people = people;
  }
}
