import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';
import { first } from 'rxjs/operators';
import { NotificationService } from '../shared/notification.service';
import { User } from '../users/user';
import { UserRegistration } from './user-registration';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;
  hide = true;
  error: string;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private notificationService: NotificationService) {

    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  ngOnInit() {
    // initialize form with default values
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: ['']
    });
  }

  register() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    const userRegistration = new UserRegistration(
      this.f.firstName.value,
      this.f.lastName.value,
      this.f.email.value,
      this.f.password.value,
      this.f.dateOfBirth.value
    );

    this.authenticationService.register(userRegistration)
      .pipe(first())
      .subscribe(
        data => {
          this.notificationService.success('User Created successfully');
          this.router.navigate(['/login']);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
  }
}
