import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../_services/authentication.service';
import { NotificationService } from '../shared/notification.service';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService,
              private notificationService: NotificationService) {
  }

  /**
   * Passes the request to the next handler in the chain by calling next.handle(request) and handles errors by piping the observable response through the catchError operator by calling .pipe(catchError()).
   * Checks if the status code is 401 and automatically logs the user out by calling this.authenticationService.logout(). After logout the application is reloaded by calling location.reload(true) which redirects the user to the login page.
   * Extracts the error message from the error response object or defaults to the response status text if there isn't an error message (err.error.message || err.statusText).
   * Tthrows an error with the error message so it can be handled by the component that initiated the request by calling throwError(error).
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request)
      .pipe(catchError(err => {
      if (err.status === 401) {
        // auto logout if 401 response returned from api
        this.authenticationService.logout();
        location.reload(true);
      } else if (err.status === 403) {
        console.log('Error', err);
        this.notificationService.error('Invalid Credentials');
      }

      const error = err.error.message || err.statusText;

      return throwError(error);
    }));
  }
}
