import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, retry, tap } from 'rxjs/operators';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  private readonly _USER_PROFILE_URL = environment.baseUrl + '/api/userprofile';

  constructor(private httpClient: HttpClient) {
  }

  private _refreshNeeded$ = new Subject<void>();

  get refreshNeeded$(): Subject<void> {

    return this._refreshNeeded$;
  }

  async loadUserProfile<T>(): Promise<any> {
    const data = await this.httpClient.get<T>(this._USER_PROFILE_URL)
      .toPromise();

    return data;
  }

  getUserProfile<T>(): Observable<T> {

    return this.httpClient.get<T>(this._USER_PROFILE_URL)
      .pipe(
        tap(() => this.refreshNeeded$.next()),
        retry(1),
        catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
