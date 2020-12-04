import { Component, OnInit } from '@angular/core';
import { QuestionService } from 'src/app/services/question.service';
import * as moment from 'moment';
import { QuestionPreviewComponent } from 'src/app/create-quiz/question-preview/question-preview.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserService } from '../../services/user.service';
import { from } from 'rxjs';
import { ImportQuestionDialogComponent } from '../import-question-dialog/import-question-dialog.component';
import { LoggerService } from 'src/app/services/logger.service';


@Component({
  selector: 'app-question-bank-view',
  templateUrl: './question-bank-view.component.html',
  styleUrls: ['./question-bank-view.component.scss']
})
export class QuestionBankViewComponent implements OnInit {
  finalResultTemp: any;
  finalResult: any;
  questionBank1: any;
  count: any;

  constructor(private questionInventory: QuestionService, public dialog: MatDialog,
              private userService: UserService, private logger: LoggerService) { }


  public loaded = false;
  public questionList;
  public udpatedQuestionList;
  public questionList1;
  public questionBank;
  public errorMsg = '';
  public editQuestionButton = true;
  userProfile: any = {};
  username: any;
  userAvatar: any;
  userProfileAvatarUrl: any;

  public page = 0;
  public limit = 60;

  ngOnInit() {
    this.getAllQuestions();
  }


  getAllQuestions() {
    this.logger.log('ngOnit called-->>');
    const url = `kaliber-question-inventory/questions?page=${this.page}&limit=${this.limit}`;
    this.questionInventory.getAllQuestions(url)
      .subscribe((res) => {
        this.loaded = true;
        this.questionList = res;
        this.udpatedQuestionList = this.questionList.result;
        this.count = this.questionList.count;
        this.logger.log('getAllques1-->>', this.udpatedQuestionList);
        this.logger.log('data of getAllmethod --> ', this.udpatedQuestionList);
        this.questionBank = this.udpatedQuestionList.map(data => {
          data.createdOn = moment(data.createdOn).fromNow();
          return data;
        });
        this.logger.log('getAkkques2-->>', this.questionBank);
        this.logger.log('getAkkqu--Date-->>', this.questionBank.createdOn);
      },
        (error) => {
          // this.logger.log('error :::', error);
          this.errorMsg = error;
        });
  }


  public onScroll(): void {
    this.logger.log('INSIDE ONSCROLLDOWN');
    this.page += 1;
    this.logger.log('count', this.count);
    this.logger.log('quest length', this.questionBank.length);
    const url = `kaliber-question-inventory/questions?page=${this.page}&limit=${this.limit}`;
    if ( this.count > this.questionBank.length) {
    this.questionInventory.getAllQuestions(url)
      .subscribe((res) => {
        this.questionList = res;
        this.udpatedQuestionList = this.questionList.result;
        this.logger.log('newMethod-->>', this.udpatedQuestionList);
        this.logger.log('data of getAllmethod --> ', this.udpatedQuestionList);
        this.questionBank1 = this.udpatedQuestionList.map(data => {
          data.createdOn = moment(data.createdOn).fromNow();
          return data;
        });
        this.questionBank1.forEach(element => {
          this.questionBank.push(element);
        });
        this.logger.log('getAkkques2-->>', this.questionBank);
        this.logger.log('getAkkqu--Date-->>', this.questionBank.createdOn);
    },
    (error) => {
      // this.logger.log('error :::', error);
      this.errorMsg = error;
    });
  }
  }

  showQuestion(questionObject) {

    const dialogRef = this.dialog.open(QuestionPreviewComponent, {
      width: '800px',
      height: '600px',
      data: { question: questionObject, showEdit: this.editQuestionButton },
    });

  }

  openDialogUpload() {
      const dialogRef = this.dialog.open(ImportQuestionDialogComponent, {
        width: '300px',
        height: '250px',
      });
    }
}



