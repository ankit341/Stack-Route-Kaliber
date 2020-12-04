import { Injectable, } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Quiz } from '../interfaces/quiz-inventory';
import { LoggerService } from './logger.service';
const httpOptions = {
 headers: new HttpHeaders({
   'Content-Type':  'application/json',
 })
};
@Injectable({
 providedIn: 'root'
})
export class QuizInventoryService {
 Url = 'kaliber-quiz-inventory/quizzes?statusvalue=OPEN';
 Url1 = 'kaliber-quiz-inventory/quizzes/creater?creatorname=';
 creatorName = 'itc';
 urlQuizSection = 'kaliber-quiz-inventory/questions/quizzes/sections';
 urlQuizCreatedBy = this.Url1 + this.creatorName;
 constructor(private httpClient: HttpClient,
             private logger: LoggerService) { }
 getQuizzes(): Observable<Quiz[]> {
   return this.httpClient.get<Quiz[]>(this.Url)
                         .pipe(catchError(this.handleError));
 }
 reportQuiz(url, body) {
  return this.httpClient.patch<[]>(url, body);
}
 archiveQuizzes(url) {
  //  const a: any;
   return this.httpClient.patch<[]>(url, httpOptions)
                          .pipe(catchError(this.handleError));
 }
 getPaginatedQuizzes(url): Observable<Quiz> {
  return this.httpClient.get<Quiz>(url)
                        .pipe(catchError(this.handleError));
 }
 getQuiz(subQuizCode): Observable<Quiz> {
  return this.httpClient.get<Quiz>(this.Url, {
    params: {
      quizCode: subQuizCode
    }
  })
 .pipe(catchError(this.handleError));
}
putQuiz(url): Observable<Quiz> {
  return this.httpClient.get<Quiz>(url)
                        .pipe(catchError(this.handleError));
}
updateQuiz(url, quizBody): Observable<[]> {
  return this.httpClient.put<[]>(url, quizBody)
                        .pipe(catchError(this.handleError));
}
 getQuizzesByCreator(): Observable<Quiz[]> {
   return this.httpClient.get<Quiz[]>(this.urlQuizCreatedBy)
                         .pipe(catchError(this.handleError));
 }
 postQuiz(quiz): Observable<[]> {
   return this.httpClient.post<[]>(this.Url, JSON.stringify(quiz), httpOptions)
                          .pipe(catchError(this.handleError));
 }
 getUserQuizzes(url: string): Observable<Quiz> {
   return this.httpClient.get<Quiz>(url);
 }
 postQuizSectionQuestion(quizSectionQuestion): Observable<any[]> {
   return this.httpClient.post<any[]>(this.urlQuizSection, quizSectionQuestion);
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
       `Backend returned code ${error.status},`  +
       `body was: ${error.error}`);
   }
   // return an observable with a user-facing error message
   return throwError(
     'There are no quizzes yet');
 }
}
