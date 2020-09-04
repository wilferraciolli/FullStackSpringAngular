import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';

@Injectable()
export class HttpBaseService {

  private headers = new HttpHeaders();
  // private SERVER_BASE_PATH = 'http://localhost:5001/api/';

  constructor(
    private router: Router,
    private httpClient: HttpClient) {
    this.headers = this.headers.set('Content-Type', 'application/json');
    this.headers = this.headers.set('Accept', 'application/json');
  }

  // getAreaUrl(): string {
  //   return '';
  // }

  getAll<T>(url: string): Observable<T> {

    return this.httpClient.get<T>(url)
      .pipe(retry(1),
        catchError(this.handleError));
  }

  getSingle<T>(url: string) {

    return this.httpClient.get<T>(url)
      .pipe(
        tap(() => console.log(`fetched by url = ${url}`)),
        retry(1),
        catchError(this.handleError));
  }


  // Promise using then
  // getById<T>(url: string): Promise<any> {
  //
  //   return this.httpClient.get<T>(url)
  //     .toPromise()
  //     .then(data => {
  //       const d = data;
  //       console.log('The value from promise is', d);
  //       return data;
  //     });
  // }

  async getById<T>(url: string): Promise<any> {

    const data = await this.httpClient.get<T>(url)
      .toPromise();

    return data;
  }

  getTemplate<T>(url: string) {

    return this.httpClient.get<T>(url)
      .pipe(retry(1),
        catchError(this.handleError));
  }

  add<T>(url: string, payloadToAdd: T) {
    console.log('Adding inside');
    console.log(payloadToAdd);

    return this.httpClient.post<T>(url, payloadToAdd, { headers: this.headers });
  }

  update<T>(url: string, payloadToUpdate: T) {
    console.log(payloadToUpdate);
    return this.httpClient.put<T>(url, payloadToUpdate, { headers: this.headers });
  }

  delete(url: string) {
    return this.httpClient.delete(url);
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

  reloadCurrentRoute() {

    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }

}
