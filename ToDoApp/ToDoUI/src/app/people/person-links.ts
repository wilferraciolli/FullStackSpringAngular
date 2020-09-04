import { Link } from '../shared/response/link';

export class PersonLinks {
  self: Link;
  updatePerson: Link;
  deletePerson: Link;
  people: Link;
  cars: Link;

  constructor(self: Link, updatePerson: Link, deletePerson: Link, people: Link, cars: Link) {
    this.self = self;
    this.updatePerson = updatePerson;
    this.deletePerson = deletePerson;
    this.people = people;
    this.cars = cars;
  }
}
