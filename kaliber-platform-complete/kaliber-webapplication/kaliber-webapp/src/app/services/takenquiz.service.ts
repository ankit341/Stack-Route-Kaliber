import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Takenquiz } from '../interfaces/takenquiz';
@Injectable({
 providedIn: 'root'
})
export class TakenquizService {

  // url = 'http://localhost:3000/quizSubmission?q=Manikanta';
  url = 'kaliber-quiz-play/quizSubmissions';
  // url = '';
  // url = `kaliber-quiz-play/quizSubmissions/Manikanta?page=${this.page}&limit=${this.limit}`;
 constructor(private http: HttpClient) { }


 getTakenQuizData(url): Observable<Takenquiz[]> {
   return this.http.get<Takenquiz[]>(url);
 }


}
