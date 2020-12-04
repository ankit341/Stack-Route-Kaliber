import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { LoggerService } from './logger.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public currentUser;
  private isAuthCorrect: BehaviorSubject<boolean> = new BehaviorSubject(false);
  constructor(private http: HttpClient,
              private logger: LoggerService) {
    this.isAuthenticated();
  }

  getUserProfile() {
    return this.http.get('/kaliber-user-management/userprofile');
  }

  getIsAuthCorrect(): Observable<any> {
    return this.isAuthCorrect;
  }

  isAuthenticated() {
    if (!this.currentUser) {
      this.getUserProfile().subscribe(userProfile => {
        this.logger.log('in isAuthenticated: ', userProfile);
        if (userProfile) {
          this.currentUser = userProfile;
          this.isAuthCorrect.next(true);
          // return true;
        } else {
          // return false;
          this.isAuthCorrect.next(false);
        }
      });
    } else {
      this.isAuthCorrect.next(false);
    }
  }
}
