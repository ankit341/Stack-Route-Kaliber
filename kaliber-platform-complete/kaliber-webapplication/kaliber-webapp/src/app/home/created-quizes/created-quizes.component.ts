import { Component, OnInit } from '@angular/core';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { UserService } from '../../services/user.service';
import { LoggerService } from 'src/app/services/logger.service';


@Component({
  selector: 'app-created-quizes',
  templateUrl: './created-quizes.component.html',
  styleUrls: ['./created-quizes.component.css']
})
export class CreatedQuizesComponent implements OnInit {
  userProfile: any = {};
  userAvatar: any;
  userProfileAvatarUrl: string;

  constructor(private quizInventory: QuizInventoryService, private router: Router,
              private userService: UserService,
              private logger: LoggerService) { }
  public noOfTimesQuizTaken = 0;
  createdQuizzes = 'createdQuizzes';
  throttle = 300;
  public loaded = false;
  // public loadedScroll = false;
  public quizzes;
  public myquizzes;
  public errorMsg = '';
  public quizUser = [];
  public firstThreeAvatarIcon;
  public firstThreeConcepts;
  public page = 0;
  public limit = 10;
  public count;
  public load = [];
  public loadedScroll;

  ngOnInit() {
    this.userService.getUserProfile().subscribe((res) => {
      this.userProfile = res;
      this.getQuizzesByCreator();

    });
    this.logger.log('s');
    this.getQuizSubmissionDetails();
  }
  getQuizzesByCreator() {

    const url = `kaliber-quiz-inventory/quizzes?page=${this.page}&limit=${this.limit}&authoredBy=${this.userProfile.username}`;
    this.quizInventory.getPaginatedQuizzes(url).subscribe(
      (res) => {
        this.loaded = true;
        this.myquizzes = res;
        this.quizzes = this.myquizzes.result;
        this.logger.log(this.quizzes);
        this.count = this.myquizzes.count;
        this.quizzes.forEach(element => {
          this.userService.getByUserName(element.createdBy).subscribe(response => {
            this.userProfile = response;
            this.userProfile = this.userProfile.result;
            this.logger.log(this.userProfile);
            this.userAvatar = this.userProfile.avatarURL;
            this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
            element.userImage = this.userAvatar;
            element.name = this.userProfile.name;
          });
        });
        this.logger.log('count', this.count);
        this.addConcept();
        this.logger.log(this.quizzes);

       },
      (error) => {
        this.logger.log('error', error);
        this.loaded = true;
        this.errorMsg = 'You have not created any quizzes, Why don\'t you create one';
      }
    );
  }
  addConcept() {
    this.quizzes.forEach(element => {
      this.logger.log(element);
      element.createDate = moment(element.createDate).fromNow();

      if (element.concepts != null) {
      if (element.concepts.length > 3) {
        element.firstThreeAvatarIcon = element.concepts.slice(0, 3);
      }
      if (element.concepts.length > 0) {
        element.firstThreeConcepts = element.concepts.slice(0, 3);
      }
    }
    });
    this.logger.log(this.quizzes);
  }

  onShare() {
    this.logger.log('on share');

  }
  onSelect(quizCode) {
    this.logger.log('on select');
    this.router.navigate(['/quizLobby', quizCode]);

  }
  getQuizSubmissionDetails() {
    // this.quizSubmissionService.getQuizSubmissionDetails()
  }
  public onScroll(): void {
    this.logger.log('INSIDE ONSCROLLDOWN');
    this.page += 1;
    if (this.count > this.quizzes.length) {
      this.loadedScroll = false;
      const url = `kaliber-quiz-inventory/quizzes?page=${this.page}&limit=${this.limit}&authoredBy=${this.userProfile.username}`;
      this.quizInventory.getPaginatedQuizzes(url).subscribe(
      (res) => {
        this.myquizzes = res;
        this.logger.log(this.myquizzes.result, '::quiz details');
        this.loadedScroll = true;
        this.myquizzes.result.forEach(element => {
            this.userService.getByUserName(element.createdBy).subscribe(response => {
              this.userProfile = response;
              this.userAvatar = this.userProfile.avatarURL;
              this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
              element.userImage = this.userProfileAvatarUrl;
              this.quizzes.push(element);
              this.logger.log(element.userImage);
            });

        });
        this.addConcept();

        // this.quizzes.push(this.myquizzes.result);
        this.logger.log('quiz', this.quizzes);
      });
    }
  }


}
