export class UserRegistration {

  firstName?: string;
  lastName?: string;
  email: string;
  password: string;
  dateOfBirth?: string;

  constructor(firstName: string, lastName: string, email: string, password: string, dateOfBirth: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
  }
}
