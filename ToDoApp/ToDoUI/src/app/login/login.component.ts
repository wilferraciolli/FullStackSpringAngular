import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  hide = true;
  returnUrl: string;
  error: string;

  /**
   * Specifies the dependencies that are required by the component as parameters, these are automatically injected by
   * the Angular Dependency Injection (DI) system when the component is created.
   * Checks if the user is already logged in by checking authenticationService.currentUserValue and redirects to the home page if they are.
   */
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) {

    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  /**
   * Is an Angular lifecycle hook that runs once after the component is created. For more info on Angular lifecycle hooks see https://angular.io/guide/lifecycle-hooks.
   * Creates a new FormGroup by calling this.formBuilder.group() and assigns it to the this.loginForm property. The parameters passed to the FormBuilder
   * tell it to create two form controls -   * username and password, the form controls are both initialised with empty strings ('') as values
   * and set to required with the Validators.required Angular validator.
   * Sets the this.returnUrl property to the value passed in the url querystring, or defaults to the home page ('/') if there isn't a value in the querystring. The return url property
   * allows you to redirect the user back to the original page they requested before logging in.
   */
  ngOnInit(): void {
    // initialize form with default values
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  /**
   * Sets the this.submitted property to true to indicate that an attempt has been made to submit the form, this property is used in the login component template to display validation errors only after the first submission has been attempted.
   * Checks if the form is valid by checking this.loginForm.invalid and prevents submission if it is invalid.
   * Sets the this.loading property to true before submitting the user credentials via the authentication service, this property is used in the login component template to display a loading spinner to the user and disable the login button.
   * Authenticates the user by calling the this.authenticationService.login() method with the username and password as parameters. The authentication service returns an Observable that we .subscribe() to for the results of the authentication. On success the user is redirected to the returnUrl by calling this.router.navigate([this.returnUrl]);. On fail the error message is stored in the this.error property to be displayed by the template and the this.loading property is reset back to false.
   * The call to .pipe(first()) unsubscribes from the observable immediately after the first value is emitted.
   */
  login() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
  }
}
