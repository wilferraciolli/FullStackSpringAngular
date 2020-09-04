import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {
  }

  /**
   * Specifies the parameters (route: ActivatedRouteSnapshot, state: RouterStateSnapshot), these are required to implement the CanActivate interface.
   * Gets the value of the current user by accessing the authenticationService.currentUserValue property.
   * Returns true if the currentUser contains a value, meaning that the user is logged in.
   * Calls this.router.navigate() to navigate to the /login route if the user is not logged in, passing the returnUrl as a query parameter so the user can be redirected back to their original requested page after logging in.
   * Returns false if the user is not logged in to cancel navigation to the current route.
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // authorised so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });

    return false;
  }
}
