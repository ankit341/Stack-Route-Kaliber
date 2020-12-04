import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FetchQuestionsService {
  private listener = new BehaviorSubject([]);
  constructor() {}

  // sendTime(timeGiven: any) {
  //   this.listener.next(timeGiven);
  // }

  sendArray(quesArray) {
    this.listener.next(quesArray);
  }

  listen(): Observable<any> {
    return this.listener;
  }

  // listenTime():Observable<any>
  // {
  //   return this.listener.asObservable();
  // }
}
