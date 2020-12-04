import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { QuizSubmission } from '../interfaces/quizSubmission';
import { QuizSubmissionQuestions } from '../interfaces/QuizSubmissionQuestions';
import { Observable, throwError } from 'rxjs';

import { catchError } from 'rxjs/operators';
import { LoggerService } from './logger.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class QuizSubmissionService {
  public url = 'kaliber-quiz-play/quizSubmissions';
  public urlQuestions = 'kaliber-quiz-play/quizSubmissionQuestions';
  public urlUsername = 'kaliber-quiz-play/quizSubmissions/{userName}';
  constructor(private http: HttpClient,
              private logger: LoggerService) { }

  getFeedback(url: any): Observable<QuizSubmission> {
    return this.http
      .get<QuizSubmission>(url)
      .pipe(catchError(this.handleError));
  }
  getAdminQuizSubmissions(url: string): Observable<QuizSubmission[]> {
    return this.http
      .get<QuizSubmission[]>(url)
      .pipe(catchError(this.handleError));
  }
  getQuizSectionQuestion(subId: any): Observable<QuizSubmissionQuestions[]> {
    // return this.http.get<any[]>(url);
    return this.http.get<QuizSubmissionQuestions[]>(this.urlQuestions, {
      params: {
        submissionId: subId
      }
    });
  }

  getQuizSubmissions(): Observable<QuizSubmission[]> {
    return this.http
      .get<QuizSubmission[]>(this.urlUsername + `$username`)
      .pipe(catchError(this.handleError));
  }
  postFeedback(url): Observable<any> {

    return this.http
      .put<void>(url, null)
      .pipe(catchError(this.handleError));
  }

  postResponses(url: string, data: any): Observable<any> {
    this.logger.log('postResponses data----> ', data);
    // tslint:disable-next-line: no-shadowed-variable
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http
      .put<any>(url, data, httpOptions)
      .pipe(catchError(this.handleError));
  }
  private handleError(error: HttpErrorResponse) {
    this.logger.log('error function called');
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      this.logger.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      this.logger.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
