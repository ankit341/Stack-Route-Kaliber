import { Component, OnInit } from '@angular/core';
import { of as observableOf } from 'rxjs';
import {
  FormBuilder,
  Validators,
  FormGroup,
  FormControl,
  FormArray,
  ValidatorFn,
  AbstractControl
} from '@angular/forms';
import { QuizInventoryService } from '../services/quiz-inventory.service';
import { MatDialog } from '@angular/material';
import { PreviewComponent } from './preview/preview.component';
import { Question } from '../interfaces/question';
import { ActivatedRoute } from '@angular/router';
import { Quiz } from '../interfaces/quiz-inventory';
import { UserService } from '../services/user.service';
import { LoggerService } from '../services/logger.service';
// import { QuizCodeValidation } from './quizCode.validation';

@Component({
  selector: 'app-create-quiz',
  templateUrl: './create-quiz.component.html',
  styleUrls: ['./create-quiz.component.css']
})
export class CreateQuizComponent implements OnInit {
  isActive = false;
  public quizCode: string;
  wager = false;
  preferences = false;
  schedule = false;
  hint = false;
  feedback = false;
  showAnswers = false;
  public confidenceWager;
  public preferencesObj;
  public quizData;
  public markdown;
  public result;
  // createQuiz: FormGroup
  confidence: FormArray;
  public quizFetchedByCode;
  public quiz ;
  public edit = false;
  userProfile;
  previousPoints;
  //////
  public quizInstructions;
  //////
  constructor(
    private quizService: QuizInventoryService,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private userService: UserService,
    private logger: LoggerService
  ) { }
  quizType = ['QUIZ', 'SURVEY'];
  FormArrayConfidence = new FormGroup({});
    points = 100;
  proficiencies: string[] = ['NOVICE', 'BEGINNER', 'EXPERT'];
  add = [];
  addQuestionPressedCount = [];
  createdQuestions = [];
  createQuiz = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.pattern(/^[a-zA-Z0-9 ]*$/)
    ]),
    quizCode: new FormControl(''
    , [
      // this.checkQuizCode.bind(this),
      Validators.required,
       Validators.pattern(/^[a-zA-Z0-9 ]*$/),
       Validators.pattern(/^\S{0,50}$/),
       Validators.minLength(10),
      //  this.quizCodeValidation()
    // ], this.checkQuizCode.bind(this)
    ]),
    quizType: new FormControl(''),
    proficiency: new FormControl(''),
    description: new FormControl(''),
    subject: new FormControl(''),
    points: new FormControl('', [
      Validators.required,
      this.pointsValid(this.previousPoints)
    ]),
    //////////
    quizInstructions: new FormControl(''),
    confidence: this.fb.array([this.createItem()]),



  });
  sectionTitle1: any;
  sectionObject: any;
  tot;
  public mydata;
  sectionArray = [];
  sectionAndQuestion = [];
  public editQuiz: any;


  ngOnInit() {

    this.quizCode = this.route.snapshot.paramMap.get('quizCode');
    this.logger.log('quizCode', this.quizCode);
    this.logger.log('edit', this.edit);
    if (this.quizCode != null) {
    this.getQuiz(this.quizCode);
 }

}
// quizCodeValidation(): ValidatorFn {
//   let x = false;
//   return (control1: AbstractControl): { [key: string]: boolean } | null => {
//     // const url = `kaliber-quiz - inventory / quizzes ? quizCode = $  {control.value};
//     const url = `kaliber-quiz-inventory/quizzes?quizCode=${control1.value}`;
//     this.quizService.getQuiz(url).subscribe((data) => {
//       this.logger.log('1');
//       x = true;
//     }, (err) => {
//       this.logger.log('2');
//       this.logger.log(this.createQuiz);
//       return null;
//     }
//     );
//     if (x === true) {
//       this.logger.log('3');
//       this.logger.log(this.createQuiz);
//       return { quizCode: true };
//     }
//     this.logger.log('4');
// };
// }
  pointsValid(params): ValidatorFn {
    // this.logger.log('a', params);
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      // this.logger.log('b', control.value);
      // this.logger.log('previo', this.previousPoints);
      // this.logger.log('params', params);
      this.userService.getUserProfile().subscribe((data) => {
        this.userProfile = data;
        this.previousPoints = this.userProfile.totalPoints;
        this.logger.log('pre', this.previousPoints);
        if (control.value !== undefined && (isNaN(control.value) || control.value > this.previousPoints)) {
          this.logger.log('h');
          return { pointsValid: false };
      }
    });
      // if (control.value !== undefined && (isNaN(control.value) || control.value > this.previousPoints)) {
      //     this.logger.log('h');
      //     return { pointsValid: false };
      // }
      if (control.value !== undefined && (isNaN(control.value) || control.value > this.previousPoints)) {
        // this.logger.log('l');
        this.logger.log(this.createQuiz);
        return { pointsValid: false };
    }
      // this.logger.log('i');

      return null;
  };
}

getQuiz(quizCode: string) {
  this.logger.log('hi', quizCode);
  const url = `kaliber-quiz-inventory/quizzes?quizCode=${quizCode}`;
  this.quizService.getPaginatedQuizzes(url).subscribe(

    (quiz: any) => {
      this.quizData = quiz,
        this.quizFetchedByCode = this.quizData.result;
      this.logger.log(quiz);
      this.logger.log('res', this.quizFetchedByCode);
      this.PutQuiz(this.quizFetchedByCode);
    },
    (err: any) => this.logger.log(err)
  );
}

PutQuiz(quiz) {
  this.logger.log('Enter to edit', quiz[0]);
  // this.quiz = quiz[0];
  this.edit = true;
  this.logger.log('Quiz Title ', quiz[0].title );
  this.createQuiz = new FormGroup({
    title: new FormControl(quiz[0].title),
    quizCode: new FormControl(quiz[0].quizCode),
    quizType: new FormControl(quiz[0].quizType),
    proficiency: new FormControl(quiz[0].proficiency),
    description: new FormControl(quiz[0].description),
    subject: new FormControl(quiz[0].subject),
    points: new FormControl(quiz[0].points),
    quizInstructions: new FormControl(quiz[0].quizInstructions)
  });
  // this.createQuiz.patchValue({
  //   title: new FormControl(quiz[0].title),
  //   quizType: new FormControl(quiz[0].quizType),
  //   description: new FormControl(quiz[0].description),
  //   subject: new FormControl(quiz[0].subject),
  //   points: new FormControl(quiz[0].points),
  //   quizInstructions: new FormControl(quiz[0].quizInstructions)
  // });
}

createItem() {
    return this.fb.group({
      label: [''],
      bonusScore: [''],
      penaltyScore: ['']
    });
  }
addItem() {
    this.confidence = this.createQuiz.get('confidence') as FormArray;
    this.confidence.push(this.createItem());
  }




  // checkQuizCode(control): ValidatorFn {
  //   this.logger.log(control.value);

  //   const url = `kaliber-quiz-inventory/quizzes/${control.value}`;
  //   this.quizService.getQuiz(url).subscribe((data) => {
  //     this.logger.log('returned data', data);
  //     this.mydata = data;
  //     this.result = this.mydata.result;
  //     if (this.result.length === 0) {
  //       this.logger.log('1');
  //       return true;
  //     } else {
  //       this.logger.log('2');
  //       return false;
  //     }

  //   });
  // }


showMessage(data: any) {
    this.logger.log('data from create-quiz component ', data);
    // this.quesChild.push(data);
    // this.sectionAndQuestion.push(data);
    if (this.sectionAndQuestion.indexOf(data) === -1) {
      this.sectionAndQuestion.push(data);
    }
  }
onSubmit() {
    // updataPoints();

    const section = [];
    section.push(this.sectionAndQuestion);
    // this.sectionArray.push(section);
    this.logger.log('ins', this.quizInstructions);
    this.createQuiz.value.section = this.sectionAndQuestion;
    this.createQuiz.value.allowWager = this.wager;
    this.preferencesObj = {
      showHints: this.hint,
      showAnswers: this.showAnswers,
      explainFeedback: this.feedback
    };
    if (this.preferences) {
      this.createQuiz.value.preferences = this.preferencesObj;
    }
    this.logger.log('from quiz  ', this.createQuiz.value);
    // this.quizService.postQuiz(this.createQuiz.value).subscribe(() => this.logger.log('posting to db'));
    // this.logger.log(this.createdQuestions);
    this.logger.log(this.sectionArray);
    this.createQuiz.value.section.forEach( (myelement) => {
      myelement.questions.forEach((myele) => {
        if (myele.questionType === 'MMCQ') {
          myele.answerOptions.forEach((optionEle) => {
            if (myele.answerKeys.indexOf(optionEle.optionContent) !== -1) {
              optionEle.checked = true;
            } else {
              optionEle.checked = false;
            }
          });
        }
      });
    });
    const dialogRef = this.dialog.open(PreviewComponent, {
      width: '750px',
      height: '575px',
      data: { quiz: this.createQuiz.value , edit: this.edit, total: this.tot}
    });
  }

onChangeWager(e) {
    if (e.checked === true) {
      this.logger.log('checked wager');
      this.logger.log('hint', this.hint);
      this.wager = true;
    } else {
      this.logger.log('unchecked wager');
      this.wager = false;
    }
  }
  showSequence(data) {
    this.tot = data;
  }
onChangeSchedule(event) {
    if (event.checked === true) {
      this.schedule = true;
    } else {
      this.schedule = false;
    }
  }
onChangePreferences(event) {
    if (event.checked === true) {
      this.preferences = true;
    } else {
      this.preferences = false;
    }
  }

remove() {
    this.add.pop();
  }
questionDetails(count: Question) {
    this.createdQuestions.push(count);
  }
}
