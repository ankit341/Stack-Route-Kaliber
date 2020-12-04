import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Question } from './question';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoggerService } from './logger.service';
@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  constructor(private http: HttpClient,
              private logger: LoggerService) { }

  url = 'kaliber-question-inventory/questions';
  subject: BehaviorSubject<any> = new BehaviorSubject([]);

  passQuestionToSection(data) {
    this.subject.next(data);
  }
  getQuestion(urlq): Observable<Question> {
    return this.http.get<Question>(urlq)
      .pipe(catchError(this.handleError));
  }
  getQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(this.url)
      .pipe(catchError(this.handleError));
  }
  receiveQuestionToSection() {
    return this.subject;
  }
  // passQuestionData(data): any {
  //   return new Observable(subscriber => {
  //     subscriber.next(data);
  //   });
  // }

  postQuiz(question): Observable<[]> {
    return this.http.post<[]>(this.url, question)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error);
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

  getAllQuestions(url): Observable<Question[]> {
    return this.http.get<Question[]>(url).pipe(catchError(this.handleError));
  }
  UpdateQuestion(urlq: string, addQuestion: any, questionId): Observable<void> {
    return this.http.put<void>(urlq, addQuestion).pipe(catchError(this.handleError));
  }
}
