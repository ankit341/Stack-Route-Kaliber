import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { QuestionService } from '../../services/question.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { QuestionComponent } from '../question/question.component';
import { FormGroup, FormControl } from '@angular/forms';
import { LoggerService } from 'src/app/services/logger.service';
@Component({
  selector: 'app-add-questions',
  templateUrl: './add-questions.component.html',
  styleUrls: ['./add-questions.component.scss']
})
export class AddQuestionsComponent implements OnInit {
  panelOpenState = false;

  public loaded = false;
  public page = 0;
  public limit = 10;
  public questionData;
  checked = false;
  public question;
  public quesArray = [];
  public tempQuestion;
  public showQuestions = false;
  public questions = [];
  public questionArr = [];
  modalScrollDistance = 2;
  modalScrollThrottle = 50;
  public showSave = true;
  constructor(private questionService: QuestionService, private dialog: MatDialog,
              private logger: LoggerService,
              private dialogReference: MatDialogRef<AddQuestionsComponent>) {
    dialogReference.disableClose = true;

  }


  @Output() questionFromAddQuestion: EventEmitter<any> = new EventEmitter();


  searchBar = new FormGroup({
    search: new FormControl('')
  });
  ngOnInit() {
    this.getAllQuestions();
  }

  my() {
    return false;
  }
  getAllQuestions() {

    const url = `kaliber-question-inventory/questions?page=${this.page}&limit=${this.limit}`;
    this.questionService.getQuestion(url).subscribe((data) => {
      this.loaded = true;
      this.questions.push(data);
      this.logger.log('questions', this.questions[0].result);
      this.questions[0].result.forEach(element => {
        element.checked = false;
        if (element.questionType === 'MMCQ') {
          element.answerOptions.forEach(answerOptionVar => {
            if (element.answerKeys.indexOf(answerOptionVar.optionContent) !== -1) {
              answerOptionVar.checked = true;
            } else {
              answerOptionVar.checked = false;
            }
          });
        }
        this.questionArr.push(element);
      });
    }, error => {
      this.logger.log('Error occured', error);
    });
  }
  // difference(a1, a2, res) {
  //   for (let i = 0; i < a1.length; i++) {
  //     if (a2.indexOf(a1[i]) === -1) {
  //       res.push(a1[i]);
  //     }
  //   }
  // }

  Close() {
    this.logger.log('question sent back', this.tempQuestion);
    this.dialogReference.close(this.tempQuestion);
  }
  Search() {
    this.logger.log('searched value1', this.searchBar.value);

    this.logger.log('searched value', this.searchBar.value.search);
  }
    AuthorQuestion() {
    this.showQuestions = true;
    this.dialogReference.close('addQuestion');
    // this.mydata = 'addQuestion';
  }
  onModalScrollDown() {
    this.logger.log('Soumik');
    this.logger.log('INSIDE ONSCROLLDOWN');
    this.page += 1;
    const url = `kaliber-question-inventory/questions?page=${this.page}&limit=${this.limit}`;
    this.questionService.getQuestion(url).subscribe((data) => {
      this.logger.log('inside page = 1', this.page);
      this.questionData = data;
      this.logger.log(this.questionData.result, '::question details');
      this.questionData.result.forEach(element => {
        this.questionArr.push(element);
      });

    }, error => {
      this.logger.log('Error occured', error);
    });
  }
  Save() {
    this.questionArr.forEach(element => {
      if (element.checked === true) {
        this.quesArray.push(element);
      }
    });
    this.logger.log('added question', this.quesArray);
    if (this.quesArray.length === 1) {
      this.dialogReference.close(this.question);
    } else {
      this.dialogReference.close(this.quesArray);
    }

  }

    onChange(event, question, index) {
    // can't event.preventDefault();
    // this.questionSelected = question;
    this.question = question;
    // this.quesArray.push(question);
    this.logger.log('onChange event.checked ' + event.checked);

    this.logger.log('onChange  ' + question);
    if (event.checked) {
      // this.quesArray.push(question);
      this.logger.log(question);
      question.checked = true;

    }
    if (event.checked === false) {

      question.checked = false;
      // this.logger.log('que', question);
      // const i = this.quesArray.indexOf(question);
      // this.quesArray.slice(i, 1);

      // this.logger.log('index', index);
      // const position = this.quesArray.indexOf(question);
      // this.logger.log('que1', this.quesArray[index]);
      // this.logger.log('que2', this.quesArray[question]);
      // this.logger.log('q a', this.quesArray);

      // // this.quesArray.slice(index, 1);
      // // this.quesArray.slice(position, 1);

      // this.logger.log(this.quesArray);
    }


  }


}
