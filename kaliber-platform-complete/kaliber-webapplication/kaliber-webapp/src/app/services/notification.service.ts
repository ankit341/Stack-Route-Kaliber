import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private httpClient: HttpClient) { }

  postNotification(url, obj): Observable<[]> {
    return this.httpClient.post<[]>(url, obj);
  }
  getUserChallenges(url): Observable<[]> {
    return this.httpClient.get<[]>(url);
  }

  getUserNotifFeed(url): Observable<[]> {
    return this.httpClient.get<[]>(url);
  }

  // private handleError(error: HttpErrorResponse) {
  //   console.log('error function called');
  //   if (error.error instanceof ErrorEvent) {
  //     // A client-side or network error occurred. Handle it accordingly.
  //     console.error('An error occurred:', error.error.message);
  //   } else {
  //     // The backend returned an unsuccessful response code.
  //     // The response body may contain clues as to what went wrong,
  //     console.error(
  //       `Backend returned code ${error.status},`  +
  //       `body was: ${error.error}`);
  //   }
  //   // return an observable with a user-facing error message
  //   return throwError(
  //     'There are no quizzes yet');
  // }
}
