import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoggerService } from './logger.service';


@Injectable({
  providedIn: 'root'
})
export class AppService {

  loginUrl = `/auth-service/login`;

  constructor(private http: HttpClient,
              private logger: LoggerService) { }

  getLogin() {
    this.logger.log('Url ::', this.loginUrl);
    // return this.http.get(this.loginUrl);
    fetch(this.loginUrl)
      .then((res) => {
        this.logger.log('RESPONSE: ', res);
      }).catch(err => {
        this.logger.log('Some error occured');
      });
  }

}
