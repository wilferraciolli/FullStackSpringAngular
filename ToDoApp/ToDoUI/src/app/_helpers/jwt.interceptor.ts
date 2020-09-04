import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../_services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) {
  }

  /**
   * Checks if the user is logged in by checking the authenticationService.currentUserValue exists and has a token property.
   * Clones the request and adds the Authorization header with the current user's JWT token with the 'Bearer ' prefix to indicate that it's a bearer token (required for JWT). The request object is immutable so it is cloned to add the auth header.
   * Passes the request to the next handler in the chain by calling next.handle(request).
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    const currentUser = this.authenticationService.currentUserValue;

    if (currentUser && currentUser.access_token && !this.authenticationService.isTokenExpired()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.access_token}`,
          'Content-Type': 'application/json',
          Accept: 'application/json'
        }
      });
    }

    return next.handle(request);
  }
}
