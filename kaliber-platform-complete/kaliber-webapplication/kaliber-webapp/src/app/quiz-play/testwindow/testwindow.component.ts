import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizfetchService } from '../../services/quizfetch.service';
import { FetchQuestionsService } from '../../services/fetch-questions.service';
import { QuizSubmissionService } from '../../services/quiz-submission.service';
import { Observable } from 'rxjs';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { MatquizplaydialogComponent } from '../matquizplaydialog/matquizplaydialog.component';
import { MatDialogRef, MatDialog } from '@angular/material';
import { LoggerService } from 'src/app/services/logger.service';
import * as moment from 'moment';

@Component({
  selector: 'app-testwindow',
  templateUrl: './testwindow.component.html',
  styleUrls: ['./testwindow.component.css']
})
export class TestwindowComponent implements OnInit, OnDestroy {
  public timeGiven: number;
  public endOfQuestionReached = false;
  public indexOfLastQuestion;
  public minutes: number;
  public seconds = 0;
  public secondsInString;
  public minutesInString;
  public questionId: number;
  public question = null;
  public prevQuestion;
  public indexOfSection = 0;
  public indexOfQuestions = 0;
  public questionNumber = 1;
  public qidArray;
  public answer;
  public isNext = false;
  public isPrevious = false;
  public allOptions;
  errorMsg: any;
  sectionArray: any;
  public step = 10;
  public thumbLabel = true;
  public max = 100;
  public min = 0;
  public tickInterval = 20;
  public disabled = false;
  public value = 20;
  public questionData;
  public confidenceWagerArray;
  public confidenceWager;
  public allowWager;
  public isFinish = false;
  public questionResponse = {
    answerOptions: [],
    confidenceWager: this.confidenceWager,
    questionId: this.questionId
  };
  public quizCode: string;
  public quizTitle;
  public checkedOptions = false;
  public checkedConfidence = false;
  public isLastQuestion = false;
  public confidenceDisabled = true;
  public handle;
  public hasData = false; // for mat-spinner
  public date = new Date();
  isActive = true;
  public sectionChange = false;
  public questionNumberArray = [
    {
      number: 1,
      bgColor: '#03a9f4',
      fgColor: '#000000'
    }
  ];

  public dialogRef: MatDialogRef<MatquizplaydialogComponent>;
  public submissionId;
  public sequence = 0;
  public totalQuestions: number;
  public lastNumber = 0;

  constructor(
    private route: ActivatedRoute,
    private fetch: QuizfetchService,
    private fetchQues: FetchQuestionsService,
    private router: Router,
    private quizSubmission: QuizSubmissionService,
    private localStorageService: LocalStorageService,
    private dialog: MatDialog,
    private logger: LoggerService
  ) { }

  question1 = {
    maxDurationMins: 2,
    maxScore: 10,
    questionContent: null,
    questionId: '2b799125-5eaa-4a8e-a936-c95874ae0041',
    questionTitle: 'what is size of int',
    questionType: 'MCQ',
    randomiseOptions: false,
    answerOptions: [
      {
        optionContent: '12bits',
        optionKey: 'dummy',
        optionReference: 'dummy'
      },
      {
        optionContent: '12bits',
        optionKey: 'dummy',
        optionReference: 'dummy'
      },
      {
        optionContent: '12bits',
        optionKey: 'dummy',
        optionReference: 'dummy'
      },
      {
        optionContent: '12bits',
        optionKey: 'dummy',
        optionReference: 'dummy'
      }
    ],
    concepts: {
      name: 'DataTypes',
      taxonomy: 'remember'
    }
  };
  confidenceWagerArray1 = [
    { label: 'confident' },
    { label: 'very confident' },
    { label: 'not confident' }
  ];
  allowWager1 = true;

  ngOnInit() {
    // if (this.localStorageService.getFromLocalStorage() != null) {
    //   this.logger.log("inside local storage top");
    //   this.hasData = true;
    //   this.question = this.localStorageService.getFromLocalStorage()[0].title;
    //   this.questionId = this.question.questionId;
    //   this.sectionArray = this.localStorageService.getQuizFromLocalStorage()[0].title;
    //   const currentDate = moment();
    //   const storedTime = moment(this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].timer);
    //   const difference = currentDate.diff(storedTime, 'seconds');
    //   this.minutes = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].durationMinutes - Math.floor(difference / 60);
    //   this.seconds = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].durationSeconds - (difference % 60);
    //   if (this.seconds < 0) {
    //     this.seconds = 60 + this.seconds;
    //     this.minutes--;
    //   }
    //   this.allowWager = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].allowWager;
    //   this.quizTitle = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].quizTitle;
    //   this.submissionId = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].submissionId;
    //   this.confidenceWagerArray = this.localStorageService.getTimerAndQuizDetailsLocalStorage()[0].confidence;
    //   this.questionNumber = this.localStorageService.getQuestionDetailsLocalStorage()[0].questionNumber;
    //   this.questionNumberArray = this.localStorageService.getQuestionDetailsLocalStorage()[0].questionNumberArray;
    //   this.logger.log("inside local storage ", this.questionNumberArray);
    //   this.sequence = this.localStorageService.getQuestionDetailsLocalStorage()[0].sequence;
    //   this.localStorageService.removeTimerAndQuizDetailsLocalStorage();
    //   this.startTimer();
    //   this.localStorageService.storeTimerAndQuizDetails(
    //     moment(), this.minutes, this.seconds, this.confidenceWagerArray, this.allowWager, this.quizTitle, this.submissionId
    //   );
    //   this.quizCode = this.route.snapshot.paramMap.get('quizCode');
    //   this.hasData = true;
    // } else {
    this.logger.log('before storing to local');
    this.quizCode = this.route.snapshot.paramMap.get('quizCode');
    this.fetchQues.listen().subscribe(data => {
      this.allowWager = data[2];
      this.confidenceWagerArray = data[3];
      this.quizTitle = data[4];
      this.sectionArray = data[0]; // storing all different section objects
      this.minutes = data[1];
      this.totalQuestions = data[5];
      this.logger.log(' question number array ', this.questionNumberArray);
      this.logger.log(' total number of questions ', this.totalQuestions);
      // this.totalQuestions = 6;
      for (let i = 2; i <= Math.min(this.totalQuestions, 5); i++) {
        this.logger.log('questionNumberArray on ngOninit ', JSON.stringify(this.questionNumberArray));
        this.questionNumberArray.push(
          {
            number: i,
            bgColor: '#e3e5e8',
            fgColor: '#000000'
          }
        );
      }
      this.submissionId = data[6];
      this.timeGiven = this.minutes;
      // this.localStorageService.storeTimerAndQuizDetails(
      //   moment(), this.minutes, 0, this.confidenceWagerArray, this.allowWager, this.quizTitle, this.submissionId
      // );
      // this.questionId = this.sectionArray[0][0].questionId;
      // tslint:disable-next-line: max-line-length
      // fetching the questionId of first question
      // tslint:disable-next-line: max-line-length
      const url = `kaliber-quiz-play/quizzes/${this.quizCode}/sections/${this.sectionArray[0][0].sectionTitle}/questions/sequence/${this.sequence}/${this.submissionId}/quizsubmissionquestions`;
      this.questionResponse.answerOptions = null;
      this.questionResponse.confidenceWager = null;
      this.questionResponse.questionId = null;
      this.logger.log('Sequence number ', this.sequence);

      // tslint:disable-next-line: no-shadowed-variable
      this.quizSubmission
        .postResponses(url, this.questionResponse)
        .subscribe(
          // tslint:disable-next-line: no-shadowed-variable
          data => {
            this.logger.log('data is ', data);
            this.questionData = data;
            this.sequence++;
            // this.localStorageService.storeQuestionDetails(this.questionNumber, this.questionNumberArray, this.sequence);
            this.question = this.questionData.result;
            this.question.answerOptions.forEach((option) => {
              option.checked = false;
            });
            this.logger.log(JSON.stringify(this.question));
            this.questionId = this.question.questionId;
            this.hasData = true;
            // this.localStorageService.storeOnLocalStorage(this.question);
            if (this.minutes === 0) {
              this.minutesInString = '00';
            }
            this.startTimer();
          }, error => {
            this.logger.log('Error occured', error);
          });
    });
    // }
  }

  next() {
    // tslint:disable-next-line: max-line-length

    // tslint:disable-next-line: max-line-length;
    const answers = [];
    if (this.sectionChange === false) {
      if (this.question.questionType === 'MCQ') {
        answers.push(this.answer);
      } else {
        this.question.answerOptions.forEach((Option) => {
          if (Option.checked) {
            answers.push(Option);
          }
        });
      }
    }
    this.logger.log('answer ' + JSON.stringify(answers));
    // tslint:disable-next-line: max-line-length
    const postUrl = `kaliber-quiz-play/quizzes/${this.quizCode}/sections/${this.sectionArray[this.indexOfSection][0].sectionTitle}/questions/sequence/${this.sequence}/${this.submissionId}/quizsubmissionquestions`;
    if (this.sectionChange === false) {
      this.questionResponse.answerOptions = answers;
      this.questionResponse.confidenceWager = this.confidenceWager;
      this.questionResponse.questionId = this.questionId;
    } else {

      this.questionResponse.answerOptions = null;
      this.questionResponse.confidenceWager = null;
      this.questionResponse.questionId = null;
      this.sectionChange = false;
    }
    this.quizSubmission
      .postResponses(postUrl, this.questionResponse)
      .subscribe(data => {
        this.confidenceWager = null;
        const nextQuestionDetails = data;
        this.question = nextQuestionDetails.result;
        this.logger.log('question Object ', JSON.stringify(this.question) + ' question number ', this.questionNumber);
        if (this.question == null) {
          if (this.indexOfSection < this.sectionArray.length - 1) {
            this.indexOfSection++;
            this.sectionChange = true;
            this.questionId = null;
            this.skip();
          } else {
            // this.localStorageService.removeOnLocalStorage();
            // this.localStorageService.removeQuizOnLocalStorage();
            // this.localStorageService.removeQuestionDetailsLocalStorage();
            // this.localStorageService.removeTimerAndQuizDetailsLocalStorage();
            const urlPostSubmission = `kaliber-quiz-play/quizSubmissions/quizzes/${this.quizCode}/${this.submissionId}`;
            this.quizSubmission.postFeedback(urlPostSubmission).subscribe(() => {
              this.router.navigate(['/quizSubmission', this.submissionId]);
            });
          }
        } else {
          this.question.answerOptions.forEach((option) => {
            option.checked = false;
          });
          this.questionId = this.question.questionId;
          this.sequence++;
          this.questionNumber++;
          this.isNext = false;
          // this.localStorageService.removeOnLocalStorage();
          // this.localStorageService.removeQuestionDetailsLocalStorage();
          if (this.questionNumberArray[this.questionNumberArray.length - 1].number < this.questionNumber) {
            // tslint:disable-next-line: variable-name
            this.lastNumber = this.questionNumberArray[this.questionNumberArray.length - 1].number;
            this.questionNumberArray.forEach((idata) => {
              idata.number = idata.number + this.questionNumberArray.length;
              idata.bgColor = '#e3e5e8';
              idata.fgColor = '#000000';
              this.questionNumberArray[0].bgColor = '#03a9f4';
              if (idata.number === this.totalQuestions) {
                this.endOfQuestionReached = true;
              }
            });
            if (this.endOfQuestionReached) {
              // tslint:disable-next-line: variable-name

              // tslint:disable-next-line: variable-name
              let number = this.questionNumberArray[this.questionNumberArray.length - 1].number;
              while (number !== this.totalQuestions) {
                this.questionNumberArray.pop();
                number--;
              }
            }
            // this.localStorageService.storeQuestionDetails(this.questionNumber, this.questionNumberArray, this.sequence);
          } else {
            this.questionNumberArray[this.questionNumber - this.lastNumber - 2].bgColor = '#e3e5e8';
            this.questionNumberArray[this.questionNumber - this.lastNumber - 2].fgColor = '#a8aaad';
            this.questionNumberArray[this.questionNumber - this.lastNumber - 1].bgColor = '#03a9f4';
            // this.localStorageService.storeQuestionDetails(this.questionNumber, this.questionNumberArray, this.sequence);
          }
          // this.localStorageService.storeOnLocalStorage(this.question);
          this.logger.log(this.questionNumberArray[this.questionNumberArray.length - 1].number, ' question number ', this.questionNumber);
        }
      }, error => {
        this.logger.log('Error occured', error);
      });
    this.confidenceDisabled = true;

  }

  skip() {
    if (this.isLastQuestion) {
      this.answer = null;
      this.confidenceWager = null;
      this.finishQuiz();
    } else {
      // tslint:disable-next-line: max-line-length
      const postUrl = `kaliber-quiz-play/quizzes/${this.quizCode}/sections/${this.sectionArray[this.indexOfSection][0].sectionTitle}/questions/sequence/${this.sequence}/${this.submissionId}/quizsubmissionquestions`;
      this.questionResponse.answerOptions = null;
      this.questionResponse.confidenceWager = null;
      this.questionResponse.questionId = this.questionId;
      this.quizSubmission
        .postResponses(postUrl, this.questionResponse)
        .subscribe(data => {
          this.confidenceWager = null;
          const nextQuestionDetails = data;
          this.question = nextQuestionDetails.result;
          if (this.question == null) {
            if (this.indexOfSection < this.sectionArray.length - 1) {
              this.indexOfSection++;
              this.sectionChange = true;
              this.next();
            } else {
              // this.localStorageService.removeOnLocalStorage();
              // this.localStorageService.removeQuizOnLocalStorage();
              // this.localStorageService.removeQuestionDetailsLocalStorage();
              // this.localStorageService.removeTimerAndQuizDetailsLocalStorage();
              const urlPostSubmission = `kaliber-quiz-play/quizSubmissions/quizzes/${this.quizCode}/${this.submissionId}`;
              this.quizSubmission.postFeedback(urlPostSubmission).subscribe(() => {
                this.router.navigate(['/quizSubmission', this.submissionId]);
              });
            }
          } else {
            this.question.answerOptions.forEach((option) => {
              option.checked = false;
            });
            this.questionId = this.question.questionId;
            this.sequence++;
            this.questionNumber++;
            this.isNext = false;
            // this.localStorageService.removeOnLocalStorage();
            // this.localStorageService.removeQuestionDetailsLocalStorage();
            if (this.questionNumberArray[this.questionNumberArray.length - 1].number < this.questionNumber) {
              // tslint:disable-next-line: variable-name
              this.lastNumber = this.questionNumberArray[this.questionNumberArray.length - 1].number;
              this.questionNumberArray.forEach((pdata) => {
                pdata.number = pdata.number + this.questionNumberArray.length;
                pdata.bgColor = '#e3e5e8';
                pdata.fgColor = '#000000';
                this.questionNumberArray[0].bgColor = '#03a9f4';
                if (pdata.number === this.totalQuestions) {
                  this.endOfQuestionReached = true;
                }
              });
              if (this.endOfQuestionReached) {
                // tslint:disable-next-line: variable-name
                let number = this.questionNumberArray[this.questionNumberArray.length - 1].number;
                while (number !== this.totalQuestions) {
                  this.questionNumberArray.pop();
                  number--;
                }
              }
              // this.localStorageService.storeQuestionDetails(this.questionNumber, this.questionNumberArray, this.sequence);
            } else {
              this.questionNumberArray[this.questionNumber - this.lastNumber - 2].bgColor = '#e3e5e8';
              this.questionNumberArray[this.questionNumber - this.lastNumber - 2].fgColor = '#a8aaad';
              this.questionNumberArray[this.questionNumber - this.lastNumber - 1].bgColor = '#03a9f4';
              // this.localStorageService.storeQuestionDetails(this.questionNumber, this.questionNumberArray, this.sequence);
            }
            // this.localStorageService.storeOnLocalStorage(this.question);
          }
        }, error => {
          this.logger.log('Error occured', error);
        });
      this.confidenceDisabled = true;
    }
  }

  finishQuiz() {
    // this.localStorageService.removeOnLocalStorage();
    // this.localStorageService.removeQuizOnLocalStorage();
    // this.localStorageService.removeQuestionDetailsLocalStorage();
    // this.localStorageService.removeTimerAndQuizDetailsLocalStorage();
    // tslint:disable-next-line: max-line-length
    let answers = [];
    if (this.question.questionType === 'MCQ') {
      if (this.answer != null) {
        answers.push(this.answer);
      } else {
        answers = null;
      }
    } else {
      this.question.answerOptions.forEach((Option) => {
        if (Option.checked) {
          answers.push(Option);
        }
      });
    }
    // tslint:disable-next-line: max-line-length
    const postUrl = `kaliber-quiz-play/quizzes/${this.quizCode}/sections/${this.sectionArray[this.indexOfSection][0].sectionTitle}/questions/sequence/${this.sequence}/${this.submissionId}/quizsubmissionquestions`;
    this.questionResponse.answerOptions = answers;
    this.questionResponse.confidenceWager = this.confidenceWager;
    this.questionResponse.questionId = this.questionId;
    this.isFinish = true;
    this.quizSubmission
      .postResponses(postUrl, this.questionResponse)
      .subscribe(() => {
        const urlPostSubmission = `kaliber-quiz-play/quizSubmissions/quizzes/${this.quizCode}/${this.submissionId}`;
        this.quizSubmission.postFeedback(urlPostSubmission).subscribe(() => {
          this.router.navigate(['/quizSubmission', this.submissionId]);
        });
      }, error => {
        this.logger.log('Error occured', error);
      });
  }

  startTimer() {
    this.asyncObservable().subscribe();
  }
  asyncObservable() {
    this.handle = setInterval(() => {
      if (this.seconds === 0) {
        this.seconds = 59;
        this.secondsInString = this.seconds;
        if (this.minutes <= 10) {
          this.minutesInString = '0' + --this.minutes;
        } else {
          this.minutesInString = --this.minutes;
        }
      } else {
        if (this.minutes < 10) {
          this.minutesInString = '0' + this.minutes;
        } else {
          this.minutesInString = this.minutes;
        }
        if (this.seconds <= 10) {
          this.secondsInString = '0' + --this.seconds;
        } else {
          this.secondsInString = --this.seconds;
        }
      }
      if (this.minutes === 0 && this.seconds === 0) {
        this.finishQuiz();
        clearInterval(this.handle);
      }
    }, 1000);
    return new Observable(observer => {
      // tslint:disable-next-line: no-unused-expression
      this.handle;
    });
  }

  onOptionSelect(event) {
    if (this.allowWager) {
      this.confidenceDisabled = false;
    } else {
      this.isNext = true;
    }
  }
  onConfidenceSelect(event) {
    this.isNext = true;
  }

  ngOnDestroy() {
    // this.localStorageService.removeOnLocalStorage();
    // this.localStorageService.removeQuizOnLocalStorage();
    // this.localStorageService.removeQuestionDetailsLocalStorage();
    // this.localStorageService.removeTimerAndQuizDetailsLocalStorage();
    clearInterval(this.handle);
  }
  confirm() {
    if (this.isFinish) {
      this.dialogRef = this.dialog.open(MatquizplaydialogComponent, {
        data: {
          message: 'Are you sure you want to finish the quiz?',
          link: ['/quizSubmission', this.submissionId]
        }
      });
    } else {
      this.dialogRef = this.dialog.open(MatquizplaydialogComponent, {
        data: {
          message: 'Are you sure you want to leave the quiz?',
          link: ['/playquiz', this.quizCode]
        }
      });
    }
    this.isFinish = false;
  }
  onCheck(event, option) {
    if (this.allowWager) {
      this.confidenceDisabled = false;
    } else {
      this.isNext = true;
    }
    if (event.checked === true) {
      option.checked = true;
    } else {
      option.checked = false;
    }
  }
}
