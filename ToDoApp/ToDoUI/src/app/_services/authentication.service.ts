import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';

import * as _ from 'lodash';
import {UserRegistration} from '../registration/user-registration';
import * as jwt_decode from 'jwt-decode';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

  private readonly _AUTHENTICATION_URL = '/api/auth';
  private readonly _REFRESH_TOKEN_URL = '/api/auth/refresh/token';

  public currentUser: Observable<any>;
  public isUserLoggedOn: boolean;
  private currentUserSubject: BehaviorSubject<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.isUserLoggedOn = !_.isNull(localStorage.getItem('currentUser'));
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  /**
   * Get the current user from the local storage.
   */
  getToken(): string {

    const currentUser = this.currentUserValue;

    if (currentUser && currentUser.access_token) {

      return this.currentUserSubject.value.access_token;
    } else {

      return null;
    }
  }

  getRefreshToken(): string {

    const currentUser = this.currentUserValue;

    if (currentUser && currentUser.refresh_token) {

      return this.currentUserSubject.value.refresh_token;
    } else {

      return null;
    }
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);

    if (decoded.exp === undefined) return null;

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) token = this.getToken();
    if (!token) return true;

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

  login(username, password) {

    return this.http.post<any>(environment.baseUrl + this._AUTHENTICATION_URL + '/signin', {username, password})
      .pipe(map(user => {

        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        this.startRefreshTokenTimer();

        return user;
      }));
  }

  refreshToken() {
    return this.http.post<any>(environment.baseUrl + this._REFRESH_TOKEN_URL, { 'refreshToken' : this.getRefreshToken() })
      .pipe(map((user) => {
        this.currentUserSubject.next(user);
        this.startRefreshTokenTimer();
        return user;
      }));
  }

  private refreshTokenTimeout;
  private startRefreshTokenTimer() {
    // parse json object from base64 encoded jwt token
    const jwtToken = jwt_decode(this.getToken());

    // set a timeout to refresh the token a minute before it expires
    const expires = new Date(jwtToken.exp * 1000);
    const timeout = expires.getTime() - Date.now() - (60 * 1000);
    this.refreshTokenTimeout = setTimeout(() => this.refreshToken().subscribe(), timeout);
  }
  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }

  //TODO not working
  async refreshTokenV1(refreshToken) {

    localStorage.removeItem('currentUser');

    let user = await this.http.post(environment.baseUrl + this._REFRESH_TOKEN_URL, {refreshToken})
      .toPromise()

    // store user details and jwt token in local storage to keep user logged in between page refreshes
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }


  logout() {
    // remove user from local storage and set current user to null
    this.stopRefreshTokenTimer();
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  register(userDetails: UserRegistration) {
    console.log(userDetails);

    return this.http.post<any>(environment.baseUrl + this._AUTHENTICATION_URL + '/register', userDetails)
      .pipe(map(user => {
        console.log(user);
        return user;
      }));
  }

}
