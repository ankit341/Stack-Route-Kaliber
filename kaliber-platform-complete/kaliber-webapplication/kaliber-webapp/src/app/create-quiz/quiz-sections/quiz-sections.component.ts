import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { QuestionComponent } from '../question/question.component';
import { MatDialog } from '@angular/material';
import { QuestionService } from '../../services/question.service';
import { Observable } from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import { AddQuestionsComponent } from '../add-questions/add-questions.component';
import { CreateQuestionComponent } from '../create-question/create-question.component';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-quiz-sections',
  templateUrl: './quiz-sections.component.html',
  styleUrls: ['./quiz-sections.component.css']
})
export class QuizSectionsComponent implements OnInit {

  isClicked = false;
  clicked = false;
  flag = false;
  sequence = -1;
  public temporaryQuestion;
  public dummyQuestion;
  @Output() questionsAndSection: EventEmitter<any> = new EventEmitter();
  @Output() numberOfsections: EventEmitter<any> = new EventEmitter();
  @Output() maxSequence: EventEmitter<any> = new EventEmitter();

  showSave = true;
  sectionSaved = false;
  selectedLinkingOption: string;
  isActive = false;
  questionArray = [];
  myQuestionArr = [];
  add = [
  ];
  quizSections = [
    {
      sectionAndQuestions: [],
      sectionSaved : false,
      scorePerSections : 0,
      questionCountPerSection: 0,
      createSection: new FormGroup({
        sectionTitle: new FormControl('', [Validators.required])
      }),
    }
  ];
  questionService: any;
  constructor(private dialog: MatDialog, private questionServ: QuestionService,
              private snackBar: MatSnackBar,
              private logger: LoggerService ) { }
  addQuestionPressedCount = [];
  public max = 0;
  remove(section, i) {
    this.logger.log('removing', section);
    this.logger.log('removing i', i);

    this.quizSections.splice(i, 1);
    // this.quizSections.remove();
  }

  ngOnInit() {
  }

  showMessage(data, section) {
    this.flag = true;
    this.logger.log('data from quiz section component ', data);
    this.questionService.receiveQuestionToSection().subscribe(dt => {
      this.logger.log('data:: ', dt);
    });
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }
  SaveSection(section, i) {

    let score = 0;
    section.createSection.value.questions = section.sectionAndQuestions;
    this.logger.log('from section   ', section.createSection.value);
    this.logger.log('hi', section);
    this.questionsAndSection.emit(section.createSection.value);
    this.logger.log(section.sectionAndQuestions);
    this.logger.log('sections', this.quizSections);
    this.logger.log('from save', this.quizSections[i]);

    this.quizSections[i].sectionSaved = true;
    const totQuestion = this.quizSections[i].sectionAndQuestions.length;
    this.quizSections[i].questionCountPerSection = totQuestion;
    this.quizSections[i].sectionAndQuestions.forEach(element => {
      score += element.maxScore;
    });
    this.quizSections[i].scorePerSections = score;
    this.logger.log('from save', this.quizSections[i]);
    this.logger.log('Soumik');
    this.maxSequence.emit(this.sequence);
    this.openSnackBar('Section saved ', ' successfully');

  }

  AddFile() {
    this.logger.log('Add File works');
  }
  AddImage() {
    this.logger.log('AddImage works');
  }
  QuestionBank() {

  }
  AddQuestion(section) {
    section.sectionSaved = false;
    const dialogRef = this.dialog.open(AddQuestionsComponent, {
      width: '1000px',
      height: '650px',
      data: { linkingOption: this.selectedLinkingOption,
              disableClose: true} } );
    this.logger.log(this.selectedLinkingOption);
    dialogRef.afterClosed().subscribe(
      mydata => {
        if (mydata === 'addQuestion') {
          const dialogRefQuestion = this.dialog.open(CreateQuestionComponent, {
            width: '800px',
            height: '650px',
            data: { showSave: this.showSave  }
          });
          dialogRefQuestion.afterClosed().subscribe(data => {
              if (data != null) {
                this.logger.log('hello', data);
                this.sequence++;
                data.sequence = this.sequence;
                if (section.sectionAndQuestions.indexOf(data) === -1) {
                section.sectionAndQuestions.push(data);
              }

              }
            });
        } else if (mydata != null && mydata !== undefined && mydata.length !== 0) {
          this.logger.log('hi', mydata);
          this.logger.log('mydata.length', mydata.length);
          this.logger.log('type of', typeof(mydata));
          if (mydata.length > 1) {
            mydata.forEach(element => {
              if (section.sectionAndQuestions.indexOf(element) === -1) {
                this.sequence++;
                element.sequence = this.sequence;
                // if (section.sectionAndQuestions.indexOf(element)) {
                section.sectionAndQuestions.push(element);
                // }
            }
            });
          } else {
            this.sequence++;
            mydata.sequence = this.sequence;
            if (section.sectionAndQuestions.indexOf(mydata) === -1) {
              section.sectionAndQuestions.push(mydata);
            }          }
          // section.sectionAndQuestions.push(this.myQuestionArr);
          this.temporaryQuestion = mydata;
          this.questionArray.push(mydata);
          this.logger.log('Dialog output of quiz section:', mydata);
          // return this.temporaryQuestion;
          this.logger.log(section.sectionAndQuestions, ' questions in a given section');
          this.logger.log(this.quizSections);
          this.logger.log('all', section.sectionAndQuestions);


       }
       }
  );
  }
  AddLinkQuestion(questionObj , section, type) {

    section.sectionSaved = false;
    const dialogRef = this.dialog.open(AddQuestionsComponent, {
      width: '1000px',
      height: '650px',
      data: { linkingOption: this.selectedLinkingOption} } );
    this.logger.log(this.selectedLinkingOption);
    dialogRef.afterClosed().subscribe(
      mydata => {
        if (mydata === 'addQuestion') {
          const dialogRefQuestion = this.dialog.open(CreateQuestionComponent, {
            width: '800px',
            height: '650px',
            data: { showSave: this.showSave  }
          });
          dialogRefQuestion.afterClosed().subscribe(data => {
              if (data != null) {
                this.logger.log('hello', data);
                data.sequence = questionObj.sequence + 1;
                if (this.sequence < questionObj.sequence + 1) {
                  this.sequence = questionObj.sequence + 1;
                }
                // data.sequence = this.sequence;
                mydata = data;
                // if (questionObj.followUpQuestion == null && questionObj.alternativeQuestion == null
                //   && questionObj.diagonisticQuestion == null) {
                //   this.sequence++;

                // }
                if (section.sectionAndQuestions.indexOf(data) === -1) {
                  section.sectionAndQuestions.push(data);
                }
                this.logger.log('all', section.sectionAndQuestions);
                this.temporaryQuestion = mydata;
                this.questionArray.push(mydata);
                this.logger.log('Dialog output of quiz section:', mydata);
                this.logger.log(section.sectionAndQuestions, ' questions in a given section');
                this.logger.log(this.quizSections);

                if (type === 'followUp') {
            this.logger.log('posted data' , this.temporaryQuestion);
            this.logger.log('fqid', this.temporaryQuestion.questionId);
            // section.sectionAndQuestions[questionObj].followUpTitle = this.temporaryQuestion.questionTitle;
            questionObj.followUpQuestion = this.temporaryQuestion.questionId;
            // section.sectionAndQuestions[questionObj].followUpId = this.temporaryQuestion.questionId;
            this.logger.log('follow Title', questionObj.followUpQuestion);
            this.logger.log('ques iobj' , questionObj);
            this.logger.log(this.quizSections, 'quizSections');
    }
                if (type === 'diag') {
      questionObj.diagonisticQuestion = this.temporaryQuestion.questionId;
      this.logger.log('diag Title', questionObj.diagonisticQuestion);
      this.logger.log(section.sectionAndQuestions);

    }
                if (type === 'alter') {
      questionObj.alternativeQuestion = this.temporaryQuestion.questionId;
      this.logger.log('alter Title', questionObj.alternativeQuestion);
      this.logger.log(section.sectionAndQuestions);
    }
              }
            });
        } else if (mydata != null && mydata.length !== 0) {
          this.logger.log('hi', mydata);
          if (mydata.length > 1) {
            mydata.forEach(element => {
              if (section.sectionAndQuestions.indexOf(element) === -1) {
              // element.sequence = this.sequence;
              element.sequence = questionObj.sequence + 1;
              if (this.sequence < questionObj.sequence + 1) {
                  this.sequence = questionObj.sequence + 1;
                }
              section.sectionAndQuestions.push(element);
              // if (questionObj.followUpQuestion == null && questionObj.alternativeQuestion == null
              //   && questionObj.diagonisticQuestion == null) {
              //   this.sequence++;
              //   }

            }
            });
          } else {
            mydata.sequence = questionObj.sequence + 1;
            if (this.sequence < questionObj.sequence + 1) {
              this.sequence = questionObj.sequence + 1;
            }
            // mydata.sequence = this.sequence;
            if (section.sectionAndQuestions.indexOf(mydata) === -1) {
              section.sectionAndQuestions.push(mydata);
            }            // if (questionObj.followUpQuestion == null && questionObj.alternativeQuestion == null
            // && questionObj.diagonisticQuestion == null) {
            // this.sequence++;
            // }
          }

          this.temporaryQuestion = mydata;
          this.questionArray.push(mydata);
          this.logger.log('Dialog output of quiz section:', mydata);
          // return this.temporaryQuestion;
          this.logger.log(section.sectionAndQuestions, ' questions in a given section');
          this.logger.log(this.quizSections);

          if (type === 'followUp') {
            this.logger.log('posted data' , this.temporaryQuestion);
            this.logger.log('fqid', this.temporaryQuestion.questionId);
            // section.sectionAndQuestions[questionObj].followUpTitle = this.temporaryQuestion.questionTitle;
            questionObj.followUpQuestion = this.temporaryQuestion.questionId;
            // section.sectionAndQuestions[questionObj].followUpId = this.temporaryQuestion.questionId;
            this.logger.log('follow Title', questionObj.followUpQuestion);
            this.logger.log('ques iobj' , questionObj);
            this.logger.log(this.quizSections, 'quizSections');
    }
          if (type === 'diag') {
      questionObj.diagonisticQuestion = this.temporaryQuestion.questionId;
      this.logger.log('diag Title', questionObj.diagonisticQuestion);
      this.logger.log(section.sectionAndQuestions);

    }
          if (type === 'alter') {
      questionObj.alternativeQuestion = this.temporaryQuestion.questionId;
      this.logger.log('alter Title', questionObj.alternativeQuestion);
      this.logger.log(section.sectionAndQuestions);
    }

       }
       }
  );

  }
  removeQuestion(sectionName, question, j) {
    // alert('Are you sure you want to remove this question?');

    this.logger.log('hi', sectionName);
    this.logger.log('hoho', sectionName.sectionAndQuestions[j]);
    this.logger.log('hello', question);
    this.logger.log('hieyy', j);
    this.logger.log('ho', this.quizSections[sectionName]);

    // this.logger.log('hoho', this.quizSections[sectionName].sectionAndQuestions);
    // this.logger.log('hoho ques', this.quizSections[sectionName].sectionAndQuestions[j]);

    sectionName.sectionAndQuestions.splice(j, 1);
    // this.quizSections[sectionName].sectionAndQuestions.splice(j, 1);
  }

  AddSection() {

    this.quizSections.push(
      {
        sectionAndQuestions: [],
        sectionSaved: false,
        scorePerSections : 0,
        questionCountPerSection: 0,
        createSection: new FormGroup({
          sectionTitle: new FormControl('', [Validators.required])
        }),
      }
    );
    // if(section.indexOf(this.createSection.value)!=-1){
    // section.push(this.createSection.value);
    // section.push(this.createdQuestions);
    // this.sectionArray.push(section);
    // this.logger.log(this.sectionArray);
    // }
  }
}

