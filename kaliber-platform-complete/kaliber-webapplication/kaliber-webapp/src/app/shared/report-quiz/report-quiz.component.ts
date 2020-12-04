import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { LoggerService } from 'src/app/services/logger.service';
@Component({
  selector: 'app-report-quiz',
  templateUrl: './report-quiz.component.html',
  styleUrls: ['./report-quiz.component.css']
})
export class ReportQuizComponent implements OnInit {
  selected = '';
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ReportQuizComponent>,
    private fb: FormBuilder,
    private quizService: QuizInventoryService,
    private logger: LoggerService
  ) { }

  options = [
    { value: 'This Quiz contains inappropriate questions' },
    { value: 'This Quiz contains questions from different concepts'},
    { value: 'This Quiz contains abusive content'}
  ];

  reportQuiz = new FormGroup({
    reportReason: new FormControl('',
      [Validators.required]),
    comment: new FormControl('', [Validators.required])
  });

  quizCode = this.data.quizCodeVar;

  ngOnInit() {
  }
  onSubmit() {
    const url = `kaliber-quiz-inventory/quizzes/${this.quizCode}/report`;
    this.quizService.reportQuiz(url, this.reportQuiz.value).subscribe(response => {
      this.logger.log('<----------Report Quiz------>', response);
    });
  }

}
