import { Component, OnInit } from '@angular/core';
import { QuizSubmissionService } from 'src/app/services/quiz-submission.service';
import * as moment from 'moment';
import { QuizfetchService } from 'src/app/services/quizfetch.service';
import { Router } from '@angular/router';
import { stringToKeyValue } from '@angular/flex-layout/extended/typings/style/style-transforms';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';
import { UserService } from 'src/app/services/user.service';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-quizsubmissionadminview',
  templateUrl: './quizsubmissionadminview.component.html',
  styleUrls: ['./quizsubmissionadminview.component.scss']
})
export class QuizsubmissionadminviewComponent implements OnInit {

  constructor(private quizSubmission: QuizSubmissionService,
              private rest: QuizfetchService,
              private router: Router,
              private userService: UserService,
              private logger: LoggerService) {

  }
  public quizSub;
  public quizSub1;
  public subId;
  public quizzes;
  public errorMsg: any;
  public days;
  public count = 0;
  public totalDataCount = 0;
  public page = 0;
  public limit = 10;
  public count1 = 0;
  loaded = false;

  public userdetails;
  public users;
  public userProfile;
  public userProfile1 = [];
  public userAvatar;
  public userProfileAvatarUrl;
  public username = [];

  ngOnInit() {
    this.display();
  }

  display() {
    const url = `kaliber-quiz-play/quizSubmissions?page=${this.page}&limit=${this.limit}`;
    this.quizSubmission.getAdminQuizSubmissions(url).subscribe((data) => {
      this.loaded = true;
      this.logger.log('inside getfeedback function');
      this.quizSub = data;
      this.logger.log(this.quizSub, '::quiz submissions details');
      this.totalDataCount = this.quizSub.count;
      this.count = this.totalDataCount;
      this.logger.log('count is::', this.totalDataCount);

      this.quizSub = this.quizSub.result.map((dataQuiz) => {
        // this.username = dataQuiz.userName;
        this.userService.getByUserName(dataQuiz.userName).subscribe(response => {
          this.userProfile = response;
          this.userProfile = this.userProfile.result;
          this.logger.log(this.userProfile);
          this.userAvatar = this.userProfile.avatarURL;
          this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
          dataQuiz.userName = this.userProfile.name;
        });

        dataQuiz.createdOn = moment(dataQuiz.quizStartTime).fromNow();
        dataQuiz.quizStartTime = moment(dataQuiz.quizStartTime).format('MMMM Do YYYY , h:mm a');
        dataQuiz.quizEndTime = moment(dataQuiz.quizEndTime).format('MMMM Do YYYY , h:mm a');
        const qcode = dataQuiz.quizCode;
        const quizurl = `kaliber-quiz-play/quizzes/${qcode}`;
        this.rest.getdata(quizurl).subscribe(dt => {
          this.quizzes = dt;
          dataQuiz.quizCode = this.quizzes.result.title;
        });
        this.logger.log('quiz is::', this.quizzes);
        return dataQuiz;
      });
      this.logger.log('QUIZSUB IS +++++ ', this.quizSub);
      if (this.count < 10) {
        this.count1 = this.count;
      } else {
        this.count1 = this.limit;
      }
    });
  }

  public onScroll(): void {

    if (this.totalDataCount > 0) {
      this.loaded = true;
      this.logger.log('INSIDE ONSCROLLDOWN');
      this.page += 1;
      const url = `kaliber-quiz-play/quizSubmissions?page=${this.page}&limit=${this.limit}`;
      this.quizSubmission.getAdminQuizSubmissions(url).subscribe(
        (data) => {
          this.logger.log('inside page = 1');
          this.quizSub1 = data;
          this.logger.log(this.quizSub1, '::quiz submissions details');

          this.quizSub1.result.map((data1) => {

            this.userService.getByUserName(data1.userName).subscribe(response => {
              this.userProfile = response;
              this.userProfile = this.userProfile.result;
              this.logger.log(this.userProfile);
              this.userAvatar = this.userProfile.avatarURL;
              this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
              data1.userName = this.userProfile.name;
            });
            data1.createdOn = moment(data1.quizStartTime).fromNow();
            data1.quizStartTime = moment(data1.quizStartTime).format('MMMM Do YYYY , h:mm a');
            data1.quizEndTime = moment(data1.quizEndTime).format('MMMM Do YYYY , h:mm a');
            const qcode = data1.quizCode;
            const quizurl = `kaliber-quiz-play/quizzes/${qcode}`;
            // tslint:disable-next-line: no-shadowed-variable
            this.rest.getdata(quizurl).subscribe(data => {
              this.quizzes = data;
              data1.quizCode = this.quizzes.result.title;
              this.logger.log('quiztitle is::', data1.quizCode);
            });

            return this.quizSub.push(data1);
          });
          this.logger.log('quizzes in onscroll is::', this.quizzes);
          this.logger.log('this.quizSUb in onscroll is +++++++++++= ', this.quizSub);
          this.totalDataCount -= this.limit;
          if (this.totalDataCount >= this.limit) {
            this.count1 = this.count1 + this.limit;
          } else if (this.totalDataCount < this.limit && this.totalDataCount > 0) {
            this.count1 = this.count1 + this.totalDataCount;
          }
        });
    }
  }

  onclick(submissionId: any) {
    const quizSubId = submissionId;
    this.router.navigate(['/quizSubmission', quizSubId]);
  }

  // toUserProfile(name: string) {
  //   this.username.forEach(data => {
  //     this.userService.getByUserName(data).subscribe(response => {
  //       this.userdetails = response;
  //       this.userdetails = this.userProfile.result;
  //       this.userProfile1.push(this.userdetails.name);
  //       this.userProfile1.forEach(users => {
  //         if (users === data) {
  //           this.router.navigate(['/myProfile']);
  //         }
  //       });
  //     });
  //   });
  // }

}
