import { Component, OnInit, Input, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.scss']
})
export class CreateQuestionComponent implements OnInit {
  question = [
    { value: 'Multiple Choice Question' },
    { value: 'Multi Select Question' },
  ];
  questionType: number;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private logger: LoggerService) { }
  showSave = this.data.showSave;

  ngOnInit() {
 //   console.log('show save', this.showSave);
  }
  showQuestionType(i) {
    this.questionType = this.question.indexOf(i);
  //  console.log('question type', this.questionType);

  }


}
