import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Iuser } from '../interfaces/profile';
import { LoggerService } from './logger.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private static readonly urlLogout = 'kaliber-user-management/logout';
  public url = 'kaliber-user-management/users/';
  public url2 = 'kaliber-user-management/users/userName/follow/users';
  public userProfileUrl = 'kaliber-user-management/userprofile';

  constructor(private http: HttpClient,
              private logger: LoggerService) { }
  getAll(): Observable<any> {
    return this.http.get(this.url).pipe(catchError(this.errorHandler));
  }
  getByUserName(userName: string): Observable<Iuser> {
    const userurl = `kaliber-user-management/users/${userName}`;
    return this.http.get<Iuser>(userurl);
  }
  updatePoints(url) {
    // const
    return this.http.patch(url, httpOptions);
  }
  addNewTopic(userName: string, topics: any) {
    this.logger.log('USERNAME-----> ', userName);
    this.logger.log('TOPICS------> ', topics);
    const dt = {
      followedTopics: topics
    };
    // this.logger.log("data::service ", dt);
    const urlForTopic = `kaliber-user-management/users/${userName}/follow/topics`;
    return this.http.patch(urlForTopic, dt, httpOptions);
  }

  deleteFollowedTopic(userName: string, topicDelete: any) {
    const dt = {
      topics: topicDelete
    };
    // this.logger.log("DT IS +++++++++++++ ", dt);
    const unfollowTopicUrl = `kaliber-user-management/users/${userName}/unfollow/topics`;
    return this.http.patch(unfollowTopicUrl, dt, httpOptions);
  }

  getUserProfile(): Observable<Iuser> {
    return this.http.get<Iuser>(this.userProfileUrl);
  }

  logUserOut() {
    return this.http.get(UserService.urlLogout);
  }
  errorHandler(error: HttpErrorResponse) {
    return throwError('Server is down. Please try again later');
  }

  toFollowUser(userName: string, userArray: any) {
    const followUsersUrl = `kaliber-user-management/users/${userName}/follow/users`;
    const dt = {
      users: userArray
    };
    return this.http.patch(followUsersUrl, dt, httpOptions);

  }

  toCheckTerms(userName) {
    const checkedUserUrl = `kaliber-user-management/users/${userName}/terms`;
    // const data ={
    //   users:terms
    // };
    return this.http.patch(checkedUserUrl, httpOptions);
  }


}
