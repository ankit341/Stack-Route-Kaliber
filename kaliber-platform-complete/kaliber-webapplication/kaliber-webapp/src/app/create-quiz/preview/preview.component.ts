import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { QuizInventoryService } from '../../services/quiz-inventory.service';
import { QuestionService } from 'src/app/services/question.service';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import { UserService } from '../../services/user.service';
import { sequence } from '@angular/animations';
import { LoggerService } from 'src/app/services/logger.service';
@Component({
  selector: 'app-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.css']
})
export class PreviewComponent implements OnInit {
  errorMsg: any;
  userProfile: any;
  public totalQuestions = 0;
  public question;
  public conceptArray = [];
  public quesId = '';
  public savedQuestion;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<PreviewComponent>,
              private quizService: QuizInventoryService,
              private questionService: QuestionService,
              private router: Router,
              private snackBar: MatSnackBar,
              private userService: UserService,
              private logger: LoggerService) { }

  edit = this.data.edit;
  total = this.data.total;
  ngOnInit() {
    this.logger.log(this.data);
    this.userService.getUserProfile().subscribe((res) => {
      this.userProfile = res;
    });
  }
  onCancel() {
    this.dialogRef.close();
  }
  my() {
    return false;
  }
  confirm() {
    if (confirm('Are you sure to delete ')) {
      this.logger.log('Implement delete functionality here');
    }
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }
  onSubmit() {
    this.logger.log('entire data', this.data);
    const sectionTitles = [];
    const questionArray = [];
    const onlysectionTitles = [];
    let tlte;
    this.total = this.total + 1;
    let duration = 0;
    let score = 0;
    this.data.quiz.section.forEach(element => {
      this.logger.log('Sections=', element.sectionTitle);
      const sectionTi = element.sectionTitle;
      tlte = {
        sectionTitle: sectionTi,
        sequence: 0,
        optional: false
      };

      sectionTitles.push(tlte);
      this.logger.log('tlte====  ', tlte.sectionTitle);
      onlysectionTitles.push(element.sectionTitle);
      this.logger.log('questions=', element.questions);
      questionArray.push(element.questions);
      this.logger.log(element.questions);
      const dummyQuestion = element.questions;
      // this.totalQuestions += element.questions.length;
      this.totalQuestions = this.total;
      this.logger.log('tot quest', this.totalQuestions);
      element.questions.forEach(a => {
        // tslint:disable-next-line: radix
        duration += parseInt (a.maxDurationMins);
        // tslint:disable-next-line: radix
        score += parseFloat (a.maxScore);
        if (a.questionType === 'MMCQ') {
          a.answerOptions.forEach((value) => {
            if (a.answerKeys.indexOf(value.optionContent) !== -1) {
              value.checked = true;
            } else {
              value.checked = false;
            }
          });
        }
      });
      element.questions.forEach(dummyElement => {
          dummyElement.concepts.name.forEach(conName => {
            if ((this.conceptArray.indexOf(conName.toLowerCase().trim()) === -1) && conName != null) {
              this.conceptArray.push(conName.toLowerCase().trim());
            }
            this.logger.log('concept arr', this.conceptArray);

          });

          this.postToQuizSectionQuestions( dummyElement, element.sectionTitle);
      });
    });
    const quiz = {
      title: this.data.quiz.title,
      quizCode: this.data.quiz.quizCode,
      quizType: this.data.quiz.quizType,
      proficiency: this.data.quiz.proficiency,
      subject: this.data.quiz.subject,
      maxScore: score,
      sections: sectionTitles,
      maxDurationMinutes: duration,
      allowWager: this.data.quiz.allowWager,
      confidenceWager: this.data.quiz.confidence,
      totalQuestions: this.totalQuestions,
      concepts: this.conceptArray,
      description: this.data.quiz.description,
      quizInstructions: this.data.quiz.quizInstructions,
      statusvalue: 'OPEN',
      visibility: 'PUBLIC',
      points: this.data.quiz.points,
      createdBy: this.userProfile.username,
      authoredBy: this.userProfile.username,
      publishedBy: this.userProfile.username,
      updatedBy: this.userProfile.username,
    };
    this.logger.log('duration   =', quiz.maxDurationMinutes);
    this.logger.log('quiz obj', quiz);
    this.quizService.postQuiz(quiz).subscribe((data) => {
      this.router.navigate(['createdquizes' ]);
      this.dialogRef.close();
      this.openSnackBar('quiz saved ', ' successfully');
      const url = `kaliber-user-management/users/${quiz.createdBy}/totalpoints/-${quiz.points}`;
      this.userService.updatePoints(url).subscribe(( pdata ) => {
        this.logger.log('points updated', pdata);
      });
      this.logger.log('posted to quiz db', data); },
      error => {
        this.errorMsg = error;
      }
    );
  }
  postToQuizSectionQuestions( questionData, tlte) {
    this.logger.log('section title --- ', this.data.quiz.section[0].sectionTitle);
    const object = {
      questionId: questionData.questionId,
      quizCode: this.data.quiz.quizCode,
      sectionTitle: tlte,
      followUpQuestion: questionData.followUpQuestion,
      diagonisticQuestion: questionData.diagonisticQuestion,
      alternativeQuestion: questionData.alternativeQuestion,
      sequence: questionData.sequence
    };
    this.logger.log('obj', object);
    this.quizService.postQuizSectionQuestion(object).subscribe((quizSectiondata) => {
      this.logger.log('posting quiz section question ', quizSectiondata);
    });
  }


  onEdit() {
    const url = `kaliber-quiz-inventory/quizzes/${this.data.quiz.quizCode}`;
    const quiz = {
      title: this.data.quiz.title,
      quizCode: this.data.quiz.quizCode,
      quizType: this.data.quiz.quizType,
      proficiency: this.data.quiz.proficiency,
      subject: this.data.quiz.subject,
      // maxScore: score,
      // sections: sectionTitles,
      // maxDurationMinutes: duration,
      allowWager: this.data.quiz.allowWager,
      confidenceWager: this.data.quiz.confidence,
      // totalQuestions: this.totalQuestions,
      // concepts: this.conceptArray,
      description: this.data.quiz.description,
      quizInstructions: this.data.quiz.quizInstructions,
      statusvalue: 'OPEN',
      visibility: 'PUBLIC',
      points: this.data.quiz.points,
      // createdBy: this.userProfile.username,
      // authoredBy: this.userProfile.username,
      // publishedBy: this.userProfile.username,
      updatedBy: this.userProfile.username,
    };
    this.quizService.updateQuiz(url, quiz).subscribe((data) => {
      this.logger.log('edited', data);
      this.router.navigate(['dashboard' ]);
      this.dialogRef.close();
      this.openSnackBar('quiz saved ', ' successfully');
    });
  }
}
