// For DB Data
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmincardsService } from '../../services/admincards.service';
import { MAT_DIALOG_DATA } from '@angular/material';
import { DialogService } from 'src/app/services/dialog.service';
import { UserService } from 'src/app/services/user.service';
import * as moment from 'moment';
import { LoggerService } from 'src/app/services/logger.service';


/////////////////
// const nisPackage = require('../../package.json');
/////////////////

@Component({
  selector: 'app-adminquizbank',
  templateUrl: './adminquizbank.component.html',
  styleUrls: ['./adminquizbank.component.css'],
  providers: [AdmincardsService]
})
export class AdminquizbankComponent implements OnInit {
  loaded = false;
  public quizzesData;
  userProfile;
  adminQuizBank = 'adminQuizBank';
  public quizzesData1;
  public quizbycode;
  public quizCode: string;
  public statusvalue;
  public firstThreeAvatarIcon;
  public Quizzes: any[];
  public activeQuizzes = [];
  public archivedQuizzes = [];
  public errorMsg = '';
  public page = 0;
  public limit = 10;
  createdBy;
  totalDataCount = 0;

  @Input() quiz;
  public quizzesData2;
  public count;
  public loadedScroll;
  count1: any;
  count2: any;
  Quizzes3: any;
  quizzesData3: any;

  constructor(
    private rest: AdmincardsService,
    private dialogService: DialogService,
    private router: Router,
    private logger: LoggerService,
    private userService: UserService
  ) {
  }
  ngOnInit() {
    this.getQuizzes();
  }
  // To get all the quizzes
  getQuizzes() {
    const Url = `kaliber-quiz-inventory/quizzes?page=${this.page}&limit=${this.limit}&statusvalue=OPEN`;
    const Url2 = `kaliber-quiz-inventory/quizzes?page=${this.page}&limit=${this.limit}&statusvalue=CLOSE`;
    this.logger.log('IN getQuizzes service');
    this.rest.getQuizzes(Url).subscribe(res => {
      this.loaded = true;
      this.quizzesData = res,
        this.Quizzes = this.quizzesData.result;
      this.count1 = this.quizzesData.count;
      this.Quizzes.forEach((quiz) => {
        this.createdBy = quiz.createdBy;
        this.userService.getByUserName(this.createdBy).subscribe((data1) => {
          this.userProfile = data1;
          this.userProfile = this.userProfile.result;
          console.log('sa', this.userProfile);
          quiz.name = this.userProfile.name;
          if (quiz.statusvalue === 'OPEN') {
            this.logger.log('quiz is', quiz);
            this.activeQuizzes.push(quiz);
          }

        });
        this.addmoment1();
        this.addmoment2();

      });
    });
    this.logger.log('IN getQuizzes service');
    this.rest.getQuizzes(Url2).subscribe(res2 => {
      this.loaded = true;
      this.quizzesData3 = res2,
        this.Quizzes3 = this.quizzesData3.result;
      this.count2 = this.quizzesData3.count;
      this.Quizzes3.forEach((quiz) => {
        if (quiz.statusvalue === 'CLOSE') {
          this.logger.log('quiz is', quiz);
          this.archivedQuizzes.push(quiz);
        }
      });
    });
    // this.logger.log('Quizzes count', this.count);
    // // this.logger.log('Quizzes', this.Quizzes.statusvalue)
    // this.addmoment();
    // this.logger.log('Quizzes are', this.Quizzes);

  }
  addmoment1() {
    this.activeQuizzes.forEach(element => {
      this.logger.log(element);
      element.createDate = moment(element.createDate).fromNow();

      if (element.concepts != null) {
        if (element.concepts.length > 3) {
          element.firstThreeAvatarIcon = element.concepts.slice(0, 3);

        }
        if (element.concepts.length > 0) {
          element.concepts = element.concepts.slice(0, 3);
        }
      }
    });
    this.logger.log('Quizzes are:', this.Quizzes);
  }
  addmoment2() {
    this.archivedQuizzes.forEach(element => {
      this.logger.log(element);
      element.createDate = moment(element.createDate).fromNow();

      if (element.concepts != null) {
        if (element.concepts.length > 3) {
          element.firstThreeAvatarIcon = element.concepts.slice(0, 3);

        }
        if (element.concepts.length > 0) {
          element.concepts = element.concepts.slice(0, 3);
        }
      }
    });
    this.logger.log('Quizzes are:', this.Quizzes);
  }
  public onScroll() {
    this.logger.log('Scroll Down Function');
    this.page += 1;
    if (this.count > this.Quizzes.length) { } {
      this.loadedScroll = false;
      const Url = `kaliber-quiz-inventory/quizzes?page=${this.page}&limit=${this.limit}`;
      this.rest.getQuizzes(Url).subscribe((data) => {
        this.logger.log('Inside page = 1');
        this.quizzesData = data,
          this.logger.log(this.quizzesData.result, 'quizzes');
        this.loadedScroll = true;
        this.quizzesData2 = this.quizzesData.result;
        this.quizzesData2.forEach(element => {
          if (element.statusvalue === 'OPEN') {
          this.activeQuizzes.push(element);
        } else {
          this.archivedQuizzes.push(element);
        }
        });
        this.count1 = this.activeQuizzes.length;
        this.count2 = this.archivedQuizzes.length;
        this.addmoment1();
        this.addmoment2();
        this.logger.log('Quizzes are', this.Quizzes);
      });
    }
  }
  // Change the Status of the quiz with quizCode instead of deleting from the database
  onDelete(quiz) {
    this.logger.log('deleteQuiz ---->', quiz);
    this.dialogService.openConfirmationDialog('Are you sure want to archive this quiz?')
      .afterClosed().subscribe(res => {
        if (res) {
          quiz.statusvalue = 'CLOSE';
          this.logger.warn('! Archived/Closed Succesfully');
        }
      });
  }
  // Edit the quiz with quizCode
  onEditQuiz(quizCode: string) {
    this.router.navigate(['/createquiz', quizCode]);
    this.logger.log('QuizCode of selected quiz is', quizCode);
  }
  // Quiz on the leaderboard
  onSelectleaderboard(quiz) {
    this.router.navigate(['/leaderboard']);
  }

  // Admin should able to play the quiz
  onPlayQuiz(quiz) {
    this.logger.log('Quizz ::', quiz);
    this.router.navigate(['/quizLobby', quiz.quizCode]);
  }

}

