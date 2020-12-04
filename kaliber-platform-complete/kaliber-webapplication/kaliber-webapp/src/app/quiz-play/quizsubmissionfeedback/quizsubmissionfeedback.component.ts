import { Component, OnInit } from '@angular/core';
import { QuizSubmissionService } from '../../services/quiz-submission.service';
import { QuizfetchService } from 'src/app/services/quizfetch.service';
import { ActivatedRoute } from '@angular/router';
export interface PeriodicElement {
  concept: string;
  remember: any;
  understand: any;
  apply: any;
  analyze: any;
  evaluate: any;
  create: any;
}
import * as lodash from 'lodash';
import { LoggerService } from 'src/app/services/logger.service';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-quizsubmissionfeedback',
  templateUrl: './quizsubmissionfeedback.component.html',
  styleUrls: ['./quizsubmissionfeedback.component.css']
})
// tslint:disable-next-line: no-unused-expression
// tslint:disable-next-line: label-position
// tslint:disable-next-line: no-unused-expression
export class QuizsubmissionfeedbackComponent implements OnInit {
  displayedColumns: string[] = [
    'concept',
    'remember',
    'understand',
    'apply',
    'analyze',
    'evaluate',
    'create'
  ];

  public dataSource: any;
  public quizsubfeedback;
  // public dataSource;
  isActive = false;
  errorMsg: any;
  subId: any;
  quizsubQuestion = [];
  question: any;
  playedQuestionObj: any;
  submissionData: any;
  submissionId: any;
  quizsubQuestionData: any;
  quizSubId: string;
  loaded = true;
  quizCode;
  playedQuestionObjData: any;
  playedQuestionObjArray = [];
  quizsubQuestionArr = [];
  completeQuestionObj = [];
  resultMessage: string;
  score = 0;
  maxScore = 0;
  total = 0;
  bonus = 0;
  penalty = 0;
  totalBonus = 0;
  totalPenalty = 0;
  allCorrect = 0;
  isFirst = true;
  quizData;
  createdBy;
  points;
  userProfile;
  percentPoints;
  counter = 0;
  showMessage = '';
  dataSource1 = [];
  congratulations = false;
  good = false;
  improve = false;

  constructor(
    private quizSubmission: QuizSubmissionService,
    private fetch: QuizfetchService,
    private route: ActivatedRoute,
    private quizService: QuizInventoryService,
    private userService: UserService,
    private logger: LoggerService
  ) { }

  ngOnInit() {
    const submissionId = this.route.snapshot.paramMap.get('submissionId');
    this.quizSubId = submissionId;
    this.logger.log('This is sub id ' + this.submissionId);
    this.logger.log('dataSrouce: ', this.dataSource);
    this.logger.log('before api call');
    const url = `kaliber-quiz-play/quizsubmissions/feedback/${this.quizSubId}`;

    this.quizSubmission.getFeedback(url).subscribe(data => {
      this.logger.log('In feedback api call');
      this.submissionData = data;
      this.logger.log(JSON.stringify(data) + ' submissionData');
      this.quizsubfeedback = this.submissionData.result;
      this.logger.log(this.quizsubfeedback, 'quiz sub');
      this.subId = this.quizsubfeedback.submissionId;
      this.logger.log('submissionID', this.subId);
      this.quizSubmission.getQuizSectionQuestion(this.subId).subscribe(
        Quesdata => {
          this.quizsubQuestionData = Quesdata;
          this.quizsubQuestion = this.quizsubQuestionData.result;
          this.logger.log('quiz submission question', this.quizsubQuestion);
          // this.logger.log('played ques', this.playedQuestionObj);
          this.quizsubQuestion.forEach(resultData => {
            this.score = this.score + resultData.score;
            this.bonus = this.bonus + resultData.wagerBonusScore;
            this.penalty = this.penalty + resultData.wagerPenaltyScore;
            this.maxScore =
              this.maxScore +
              resultData.question.maxScore +
              resultData.wagerBonusScore;
          });
          // this.score = this.score - this.bonus;
          this.allCorrect = this.score - this.bonus + this.penalty;
          this.total = (this.score / this.maxScore) * 100;
          this.logger.log('total', this.total);

          // tslint:disable-next-line: no-shadowed-variable
          this.dataSource = this.quizsubQuestion.map(data => {
            return {
              // tslint:disable-next-line: object-literal-key-quotes
              userAnswer: data.userAnswerContent,
              // tslint:disable-next-line: object-literal-key-quotes
              correct: data.correct,
              // tslint:disable-next-line: object-literal-key-quotes
              skipped: data.skipped,
              // tslint:disable-next-line: object-literal-key-quotes
              concepts: lodash.merge(data.question.concepts)
            };
          });
          this.dataSource.forEach(element => {
            this.logger.log('element :::::::', element);
            if (element.userAnswer !== null || element.skipped) {
              this.dataSource1.push(element);
            }
            if (element.correct === false) {
              this.counter++;
            }
          });
          this.logger.log('couter :: ', this.counter);
          this.logger.log('dataSource1 ::', this.dataSource1);
          if (this.counter >= 1) {
            this.showMessage = 'not up to expectations';
          } else {
            this.showMessage = 'Good';
          }
          if (this.total >= 50) {
            this.congratulations = true;
            this.logger.log('message', this.congratulations);
          }
          if (this.total >= 35 && this.total < 50) {
            this.good = true;
            this.logger.log('message', this.good);
          }
          if (this.total < 35) {
            this.improve = true;
            this.logger.log('message', this.improve);
          }
          this.loaded = false;
          this.upDatePoints(this.quizsubfeedback.quizCode, this.quizsubfeedback.userName);
        },
        error => {
          // this.logger.log('error :::', error);
          this.errorMsg = error;
        }
      );
    });
  }
  upDatePoints(quizCode, userName) {
    const url = `kaliber-quiz-inventory/quizzes?quizCode=${quizCode}`;
    this.quizService.getPaginatedQuizzes(url).subscribe((data) => {
      this.quizData = data;
      this.quizData = this.quizData.result;
      this.createdBy = this.quizData[0].createdBy;
      this.points = (this.quizData[0].points / 10);
      const urlPointsCreater = `kaliber-user-management/users/${this.createdBy}/totalpoints/${this.points}`;
      this.userService.updatePoints(urlPointsCreater).subscribe((pdata) => {
        this.logger.log('points creater updated', pdata);
      });
      // tslint:disable-next-line: max-line-length
      this.percentPoints = (this.quizsubfeedback.correctlyAnsweredQuestions / this.quizsubfeedback.totalQuestions) * this.quizData[0].points;
      const urlPointsPlayer = `kaliber-user-management/users/${userName}/totalpoints/${this.percentPoints}`;
      this.userService.updatePoints(urlPointsPlayer).subscribe((pdata) => {
        this.logger.log('points player updated', pdata);
      });
      // });
    });
  }

}

