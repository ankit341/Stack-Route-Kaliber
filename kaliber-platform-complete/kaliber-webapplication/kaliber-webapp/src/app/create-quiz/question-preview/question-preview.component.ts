

import { Component, OnInit, OnChanges, Output, EventEmitter, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { QuestionService } from '../../services/question.service';
import { Question } from '../../interfaces/question';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PreviewComponent } from '../preview/preview.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponentComponent } from '../snackbar-component/snackbar-component.component';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { from } from 'rxjs';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-question-preview',
  templateUrl: './question-preview.component.html',
  styleUrls: ['./question-preview.component.scss']
})
export class QuestionPreviewComponent implements OnInit {
  public errorMsg;
  public question;
  public markdown;
  durationInSeconds = 5;
  // variable made for edit button for question-bank-preview
  public editQuestionButton = this.data.showEdit;
  @Output() myQuestions: EventEmitter<Question> = new EventEmitter();
  markOption: any;

  ngOnInit(): void {
    // throw new Error('Method not implemented.');
    this.logger.log('data:: ', this.data);
    this.markdown = this.data.question.questionContent;
    this.markOption = this.data.question.answerOptions.optionContent;

  }

  constructor(
    private questionService: QuestionService,
    @Inject(MAT_DIALOG_DATA) public data,
    private dialogRef: MatDialogRef<QuestionPreviewComponent>,
    private snackBar: MatSnackBar,
    private router: Router,
    private logger: LoggerService) { }

  onSubmit() {
    this.questionService.postQuiz(this.data.question).subscribe((data) => this.logger.log('successfully added', data),

      (error) => {

        this.errorMsg = 'Server is Down';
      });
  }
  my() {
    return false;
  }
  openSnackBar() {
    this.snackBar.openFromComponent(SnackbarComponentComponent, {
      duration: this.durationInSeconds * 1000,
    });
  }
  onNoClick(): void {

    this.dialogRef.close();


    this.logger.log(this.data);


  }
  editQuestion(questionObject) {
    if (questionObject.questionType === 'MCQ') {
      this.router.navigate(['/editquestion', { questionId: questionObject.questionId, showEdit: '1' }]);
    } else {
      this.router.navigate(['/editmultquestion', { questionId: questionObject.questionId, showEdit: '1' }]);
    }
    this.dialogRef.close();
    this.logger.log(' questionId is :: ', questionObject);
  }
  //   getQuestionId(questionId: number) {
  // this.questionService.getQuestionById(questionId);
  //   }
 }

