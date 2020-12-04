import {
  Component,
  OnInit,
  OnChanges,
  Output,
  EventEmitter,
  Inject,
  Input
} from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  FormArray,
  FormBuilder
} from '@angular/forms';
import { QuestionService } from '../../services/question.service';
import { Subject } from 'rxjs';
import { Question } from '../../interfaces/question';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { PreviewComponent } from '../preview/preview.component';
import { QuestionPreviewComponent } from '../question-preview/question-preview.component';
import { ActivatedRoute } from '@angular/router';
import { AddQuestionsComponent } from '../add-questions/add-questions.component';
import { MatChipInputEvent } from '@angular/material/chips';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { LoggerService } from 'src/app/services/logger.service';

export interface IAlignment {
  optionKey: string;
  optionContent: string;
  optionReference: string;
}
// tslint:disable-next-line: no-empty-interface
export interface Fruit {}

export interface DialogData {
  animal: string;
  name: string;
}

export interface QustionType {
  value: string;
}
export interface Dificultylevel {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    private dialogRef: MatDialogRef<QuestionComponent>,
    private questionService: QuestionService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private logger: LoggerService
  ) {}
  withoutmarkdown = true;
  withmarkdown = false;
  @Output() myQuestions: EventEmitter<Question> = new EventEmitter();
  @Input() showSave;

  public ansKey = [];
  public ansOptions = [];
  public uniqueOption = [];
  contentOption: FormArray;
  public answerKeysOption = [];
  animal: string;
  name: string;
  showEdit: any;
  public transform = [];
  public markdown;
  public checked;
  public questionAdd = [];
  public questionType;
  // public addquestion = [];
  public quesChild = [];
  showId = false;
  showMcq = true;
  showAns = false;
  mark = false;
  write = false;
  public errorMsg;
  public questionOption;
  public optionCount = [1];
  public correctAnswer = [];
  public questionData; // question Object to be posted db
  public questionId;
  public showEditChoice = false;
  public showEditOption;
  public addOptoinschild = [
    {
      optionKey: 'dummy',
      optionContent: '',
      optionReference: 'dummy'
    }
  ];

  public addcard = [
    {
      optionCount: [1],
      addQuestion: new FormGroup({
        questionTitle: new FormControl('', [Validators.required]),
        // subject: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9 ]*$/)]),
        questionContent: new FormControl(''),
        conname: new FormControl(''),
        answer: new FormControl(''),
        contaxonomy: new FormControl(''),
        concepts: new FormControl(''),
        questionType: new FormControl('MCQ'),
        maxScore: new FormControl('', [
          Validators.required,
          Validators.pattern(/^[0-9 ]*$/)
        ]),
        maxDurationMins: new FormControl('', [
          Validators.required,
          Validators.pattern(/^[0-9 ]*$/)
        ]),
        difficultyLevel: new FormControl(''),
        hintContent: new FormControl(''),
        feedbackContent: new FormControl(''),
        // answerKeys: new FormControl(''),
        // answerOptions: new FormControl(''),
        content: new FormControl(''),
        answerOptions: this.fb.array([this.createItem()])

        // answerKeys: new FormArray([
        //   new FormControl('')
        // ]),
      })
    }
  ];

  public concepts = { name: [], taxonomy: '' };

  public quesData;
  question = [{ value: 'MCQ' }, { value: 'MMCQ' }];
  difficultyLevel = [
    { value: 'Beginner' },
    { value: 'Intermediate' },
    { value: 'Expert' }
  ];

  taxonom = [
    { value: 'Remember' },
    { value: 'Understand' },
    { value: 'Apply' },
    { value: 'Analyze' },
    { value: 'Evaluate' },
    { value: 'Create' }
  ];
  questionFetchedByCode: any;
  QuestionComponent: any;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  fruits: Fruit[] = [];
  createItem(): FormGroup {
    return this.fb.group({
      optionContent: ['', Validators.required],
      optionKey: 'dummy',
      optionReference: 'dummy'
    });
  }
  addOption() {
    this.contentOption = this.addcard[0].addQuestion.get(
      'answerOptions'
    ) as FormArray;

    //    this.logger.log('content', this.contentOption);

    // this.logger.log('uniqueoption', this.uniqueOption);
    // this.logger.log('number of options', this.contentOption.length);
    this.contentOption.push(this.createItem());
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      if (this.fruits.indexOf(value.trim()) === -1) {
        this.fruits.push(value.trim());
      }
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(fruit: Fruit): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
  ngOnInit() {
    // this.logger.log('SHOW SAVE', this.showSave);
    this.questionId = this.route.snapshot.paramMap.get('questionId');
    // this.logger.log('quizCode', this.questionId);
    if (this.questionId != null) {
      this.getQuestion(this.questionId);
    }
    this.showEditOption = this.route.snapshot.paramMap.get('showEdit');
    // this.logger.log('showEdit', this.showEditOption);
    if (this.showEditOption === '1') {
      this.showEditChoice = true;
      // this.logger.log(this.showEditChoice);
    }
  }

  removeGroup(index) {
    // this.logger.log('index', index);
    const delOption = this.addcard[0].addQuestion.value.answerOptions[index]
      .optionContent;

    if (this.answerKeysOption.indexOf(delOption) !== -1) {
      console.log(this.answerKeysOption);
      this.answerKeysOption = [];
    }
    const form = this.addcard[0].addQuestion.get('answerOptions') as FormArray;
    form.removeAt(index);
  }

  // clearOption(i) {

  // }

  getQuestion(questionId: string) {
    // this.logger.log('hi', questionId);
    const urlq = `kaliber-question-inventory/questions/${questionId}`;
    this.questionService.getQuestion(urlq).subscribe(
      (questionOption: any) => {
        (this.questionData = questionOption),
          (this.questionFetchedByCode = this.questionData.result);
        // this.logger.log(questionOption);
        this.PutQuiz(this.questionFetchedByCode);
      },
      (err: any) => this.logger.log(err)
    );
  }

  PutQuiz(questionOption) {
    // this.logger.log('Enter to edit', questionOption);
    // this.logger.log('QUESTION CONTENT IS ', questionOption.questionContent);
    this.addcard.pop();
    this.addcard.push({
      optionCount: [4],
      addQuestion: new FormGroup({
        questionTitle: new FormControl(questionOption.questionTitle, [
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9 ]*$/)
        ]),
        // subject: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9 ]*$/)]),
        questionContent: new FormControl(questionOption.questionContent),
        conname: new FormControl(''),
        answer: new FormControl(''),
        contaxonomy: new FormControl(questionOption.concepts.taxonomy),
        concepts: new FormControl(),
        questionType: new FormControl('MCQ'),
        maxScore: new FormControl(questionOption.maxScore, [
          Validators.required,
          Validators.pattern(/^[0-9 ]*$/)
        ]),
        maxDurationMins: new FormControl(questionOption.maxDurationMins, [
          Validators.required,
          Validators.pattern(/^[0-9 ]*$/)
        ]),
        difficultyLevel: new FormControl(questionOption.difficultyLevel),
        hintContent: new FormControl(''),
        feedbackContent: new FormControl(''),
        answerOption: new FormControl('')
        // answerOptions: this.fb.array([this.createItem()]),
      })
    });
    this.addcard[0].addQuestion.setControl(
      'answerOptions',
      this.setExistingAlignment(questionOption.answerOptions)
    );
    this.fruits = questionOption.concepts.name;
    this.concepts.name = this.fruits;
    this.concepts.taxonomy = questionOption.taxonomy;
    this.addcard[0].addQuestion.value.concepts = this.concepts;
  }
  setExistingAlignment(alignmentSet: IAlignment[]): FormArray {
    const formArray = new FormArray([]);
    alignmentSet.forEach(s => {
      formArray.push(
        this.fb.group({
          optionKey: s.optionKey,
          optionContent: s.optionContent,
          optionReference: s.optionReference
        })
      );
    });
    return formArray;
  }

  showMessage(data) {
    // this.logger.log('data from option component ', data);

    this.quesChild.push(data); // adds array of options from Option component
    // this.logger.log('check data from child ', this.quesChild);

    // this.quesChild
  }

  creatingQuestion() {}
  addCard() {
    this.addcard.push({
      optionCount: [1],
      addQuestion: new FormGroup({
        questionTitle: new FormControl(''),
        // subject: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9 ]*$/)]),
        questionContent: new FormControl(''),
        conname: new FormControl(''),
        answer: new FormControl(''),
        contaxonomy: new FormControl(''),
        concepts: new FormControl(''),
        questionType: new FormControl(''),
        maxScore: new FormControl(''),
        maxDurationMins: new FormControl(''),
        difficultyLevel: new FormControl(''),
        hintContent: new FormControl(''),
        feedbackContent: new FormControl(''),
        answerOption: new FormControl(''),

        content: new FormControl('')
        // answerKeys: new FormControl(''),
        // answerKeys: new FormArray([
        //   new FormControl('')
        // ]),
      })
    });
    // this.showAns = false;
    // this.showId = false;
  }
  clearcard() {
    this.addcard.pop();
  }
  reset(i) {
    i.addQuestion.reset();
  }

  AddOption(i) {
    i.optionCount.push(1);
  }
  Close() {
    this.dialogRef.close();
  }

  onSave(i) {
    this.concepts.name = this.fruits;
    i.addQuestion.value.concepts = this.concepts;
    this.concepts.taxonomy = i.addQuestion.value.contaxonomy;
    // this.logger.log('answer in save', this.answerKeysOption);
    i.addQuestion.value.answerKeys = this.answerKeysOption;

    // this.logger.log('dial', i.addQuestion.value);

    // i.addQuestion.value.answerKeys = this.ansKey;

    this.quesChild = [];
    this.correctAnswer = [];
    // this.logger.log('question', i.addQuestion.value);
    this.questionService.postQuiz(i.addQuestion.value).subscribe(
      data => {
        this.quesData = data;
        // this.logger.log('data from question', data);
        // this.logger.log('quesdata', this.quesData.result);
        this.dialogRef.close(this.quesData.result);
      },
      error => {
        this.errorMsg = 'Server is Down';
      }
    );
    // this.questionService.passQuestionToSection(i.addQuestion.value);
  }

  toggleId(i) {
    i[0] = true;
    i[1] = false;
  }
  shortans(i) {
    i[0] = false;
    i[1] = true;
  }
  showQuestionType(i, type) {
    i.questionType = type.value;
  }

  marker() {
    this.write = false;
    this.mark = true;
  }
  writeContent() {
    // this.logger.log('hi');
    this.write = true;
    this.mark = false;
  }
  selectAnswerKeys(i, j) {
    // let store = i.addQuestion.value.answerOptions[j];
    this.answerKeysOption = [];
    // this.logger.log('hello', j, i.addQuestion.value.answerOptions[j]);
    this.answerKeysOption.push(
      i.addQuestion.value.answerOptions[j].optionContent
    );
    // this.logger.log(this.answerKeysOption);
    // this.logger.log(i, 'answerKey',i.value);
  }
  showMarkdown() {
    this.withoutmarkdown = false;
    this.withmarkdown = true;
  }
  openDialog(i): void {
    // for (let k = 0; k < this.contentOption.length; i++) {
    //   // this.logger.log('option', this.contentOption.value[i].optionContent);
    //   if (this.uniqueOption.indexOf(this.contentOption.value[k].optionContent) === -1) {
    //     this.uniqueOption.push(this.contentOption.value[k].optionContent);

    //   }

    // }

    // this.logger.log('dialog', i);
    this.concepts.name = this.fruits;
    i.addQuestion.value.concepts = this.concepts;
    this.concepts.taxonomy = i.addQuestion.value.contaxonomy;
    i.addQuestion.value.answerKeys = this.answerKeysOption;
    this.quesChild = [];
    const dialogRef = this.dialog.open(QuestionPreviewComponent, {
      width: '600px',
      height: '600px',
      data: { question: i.addQuestion.value }
    });

    dialogRef.afterClosed().subscribe(result => {
      // this.logger.log('The dialog was closed');
      // this.animal = result;
      // this.logger.log(this.addCard);
    });
  }

  apendAnswer(answer) {
    // tslint:disable-next-line: no-unused-expression
    this.checked;
    this.correctAnswer.push(answer.quesChild.value);
    this.logger.log('correct answer', this.correctAnswer);
  }
  editQuestion() {
    this.questionId = this.route.snapshot.paramMap.get('questionId');
    this.logger.log(this.questionId, 'in Update function');
    const urlq = `kaliber-question-inventory/questions/${this.questionId}`;
    // this.concepts.name = this.addcard[0].addQuestion.value.conname;

    // this.concepts.taxonomy = this.addcard[0].addQuestion.value.contaxonomy;
    // this.addcard[0].addQuestion.value.concepts = this.concepts;
    this.questionService
      .UpdateQuestion(urlq, this.addcard[0].addQuestion.value, this.questionId)
      .subscribe(data => this.logger.log('successfully added', data));
  }
  mcqOption(i) {
    i.addQuestion.value.answerOptions = this.quesChild;
    // i.addQuestion.value.answerKeys = this.quesChild;
    // i.addQuestion.value.concepts = this.concepts;
    this.questionAdd.push(i.addQuestion.value);

    this.correctAnswer.push(i.addQuestion.value.answer);
    i.addQuestion.value.answerKeys = this.correctAnswer;

    i.addQuestion.value.answerOption = this.quesChild;
    this.logger.log('dial', i.addQuestion.value);

    i.addQuestion.value.answerOptions.forEach((d, j) => {
      this.ansOptions.push(d.addOptions[0]);

      if (d.answerKeys.length > 1) {
        this.ansKey.push(d.answerKeys[d.answerKeys.length - 1]);
      }
      if (j === i.addQuestion.value.answerOptions.length - 1) {
        this.logger.log('answerKeys====> ', this.ansKey);
        this.logger.log('answerOptions====> ', this.ansOptions);
      }
    });
    i.addQuestion.value.answerKeys.push(this.ansKey[this.ansKey.length - 1]);
    this.logger.log(i.addQuestion.value.answerKeys, 'answer');
  }
  mcaOption(i) {
    i.addQuestion.value.answerOptions = this.quesChild;
    // i.addQuestion.value.answerKeys = this.quesChild;
    // i.addQuestion.value.concepts = this.concepts;
    this.questionAdd.push(i.addQuestion.value);

    this.correctAnswer.push(i.addQuestion.value.answer);
    i.addQuestion.value.answerKeys = this.correctAnswer;

    i.addQuestion.value.answerOption = this.quesChild;
    this.logger.log('dial', i.addQuestion.value);

    i.addQuestion.value.answerOptions.forEach((d, j) => {
      this.ansOptions.push(d.addOptions[0]);

      if (d.answerKeys.length > 1) {
        this.ansKey.push(d.answerKeys[d.answerKeys.length - 1]);
      }
      if (j === i.addQuestion.value.answerOptions.length - 1) {
        this.logger.log('answerKeys====> ', this.ansKey);
        this.logger.log('answerOptions====> ', this.ansOptions);
      }
    });
    i.addQuestion.value.answerKeys = this.ansKey;
  }
}
