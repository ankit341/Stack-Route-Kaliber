import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Leaderboard } from '../interfaces/leaderboard';
import { LoggerService } from './logger.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  urlGetLeaderBoard = 'kaliber-leaderboard/leaderboard/';

  urlGetQuiz = 'kaliber-quiz-inventory/quizzes';

  constructor(private http: HttpClient,
              private logger: LoggerService) { }

  subject: BehaviorSubject<any> = new BehaviorSubject([]);

  getQuizByQuizCode(quizcode): Observable<any> {
    return this.http.get<any>(this.urlGetQuiz, {
      params: {
        quizCode: quizcode,
      }
    }).pipe(catchError(this.handleError));
  }

  getLeaderBoard(quizCode): Observable<any> {
    return this.http.get<any>(this.urlGetLeaderBoard + quizCode).pipe(catchError(this.handleError));
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
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  }
}
