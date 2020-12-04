import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';

import { catchError } from 'rxjs/operators';
import { Quizlobbyapi } from '../interfaces/Quizlobbyapi';
import { Question } from '../interfaces/question';
import { LoggerService } from './logger.service';

@Injectable()
export class QuizfetchService {
  constructor(private http: HttpClient,
              private logger: LoggerService) {}

  getdata(url: string): Observable<Quizlobbyapi> {
    return this.http.get<Quizlobbyapi>(url).pipe(catchError(this.handleError));
  }

  getQuizSectionQuestion(
    url: string
  ): Observable<any[]> {
    // return this.http.get<any[]>(url);
    return this.http.get<any[]>(url);
  }

  getQuestions(url: string): Observable<Question> {
    return this.http.get<Question>(url).pipe(catchError(this.handleError));
  }

  postAnswer(url: string, answer: string): Observable<void> {
    return this.http.put<void>(url, answer).pipe(catchError(this.handleError));
  }

  startQuiz(url: string): Observable<any> {
    return this.http.post<any>(url, null).pipe(catchError(this.handleError));
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
        `Backend returned code ${error.status}, ` + `body was: ${JSON.stringify(error.error)}`
      );
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
