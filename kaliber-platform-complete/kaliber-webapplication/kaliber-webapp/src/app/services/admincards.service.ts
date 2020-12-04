// For DB Data
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, from } from 'rxjs';

@Injectable()
// providedIn: 'root'
export class AdmincardsService {
  public Quizzes: any[] = [];
  public quiz = this.Quizzes;
  // Url = 'kaliber-quiz-inventory/quizzes';
  quizCode = '';
  constructor(private httpClient: HttpClient) {}

  getQuizzes(Url): Observable<any[]> {
    return this.httpClient.get<any[]>(Url);
  }
}
