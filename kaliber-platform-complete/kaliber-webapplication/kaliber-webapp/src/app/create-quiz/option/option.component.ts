import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { QuestionService } from 'src/app/services/question.service';
import { LoggerService } from 'src/app/services/logger.service';
@Component({
  selector: 'app-option',
  templateUrl: './option.component.html',
  styleUrls: ['./option.component.css']
})
export class OptionComponent implements OnInit {

  @Input() typeChecking;
  @Input() options;
  constructor(private logger: LoggerService) { }
  public addoption = [];
  public optionAdd = [0];
  public checked;
  public answer = [];
  public content;
  public optionSave = true;
  public isClicked = false;
  public makdown = false;
  public withoutMarkdown = true;
  public addOptoinschild = [
    {
      optionKey: 'dummy',
      optionContent: '',
      optionReference: 'dummy'
    }

  ];
  question = [
    { value: 'MCQ' },
    { value: 'Multiple Correct Answer' },
  ];

  Options = new FormGroup({


    content: new FormControl(''),

    answerKeys: new FormControl(''),
    addOptions: new FormControl(''),

  });
  // addOptions = new FormGroup({
  //   optionKey: new FormControl('dummy'),
  //   optionContent: new FormControl(''),
  //   optionReference: new FormControl('dummy'),


  // });

  @Input() questionType;

  @Output() countChanged: EventEmitter<QuestionService> = new EventEmitter();
  @Output() mydata = new EventEmitter<any>();

  updateParent() {
    this.addOptoinschild = [
      {
        optionKey: 'dummy',
        optionContent: this.Options.value.content,
        optionReference: 'dummy'
      }
    ];
    this.Options.value.addOptions = this.addOptoinschild;
    this.Options.value.answerKeys = this.answer;
    this.logger.log(this.mydata);
    this.mydata.emit(this.Options.value);
    this.logger.log(this.Options.value);
    this.optionSave = false;
  }


  Add(Options) {
    this.logger.log('inside add fn: ', Options);
    this.addoption.push(1);
    this.optionAdd.push(this.Options.value);
  }
  clear() {
    this.addoption.pop();
  }
  append(data) {
    // this.checked;
    if (this.answer.indexOf(this.Options.value.content) === -1) {
      this.answer.push(this.Options.value.content);
      this.logger.log('append---', data.value.content, this.answer);
    }
    this.logger.log('appenddddddeededed---', data.value.content, this.answer);

  }
  showMarkdown() {
    this.makdown = true;
    this.withoutMarkdown = false;
  }
  ngOnInit() {
    this.logger.log('this.options.value', this.options);
    if (this.options.length > 0) {
      this.options.forEach(dt => {
        this.Options = new FormGroup({
          content: new FormControl(dt),
          answerKeys: new FormControl(''),
          addOptions: new FormControl(''),
        });
      });
    }
  }
}
