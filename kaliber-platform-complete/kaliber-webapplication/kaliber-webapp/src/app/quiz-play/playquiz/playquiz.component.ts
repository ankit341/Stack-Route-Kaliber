import { Component, OnInit } from '@angular/core';
import { QuizfetchService } from '../../services/quizfetch.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FetchQuestionsService } from '../../services/fetch-questions.service';
import { error } from '@angular/compiler/src/util';
import { MatDialogRef, MatDialog } from '@angular/material';
import { MatquizplaydialogComponent } from '../matquizplaydialog/matquizplaydialog.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { LoggerService } from 'src/app/services/logger.service';

export interface PeriodicElement {
  sections: string;
  totalquestions: number;
  maxmarks: number;
  concepts: any[];
}
const ELEMENT_DATA: PeriodicElement[] = [
  {
    sections: 'java',
    totalquestions: 10,
    maxmarks: 10,
    concepts: ['datatypes']
  }
];

@Component({
  selector: 'app-playquiz',
  templateUrl: './playquiz.component.html',
  styleUrls: ['./playquiz.component.css'],
  providers: [QuizfetchService]
})
export class PlayquizComponent implements OnInit {
  displayedColumns: string[] = [
    'sections',
    'totalquestions',
    'maxmarks',
    'concepts'
  ];
  public dialogRef: MatDialogRef<MatquizplaydialogComponent>;
  public quizCode: string;
  public quiz;
  public id;
  public errorMsg = '';
  public QuizSections = [];
  public quizData: any;
  public sections: any;
  public allowWager;
  public confidenceWager;
  public hasData = false;
  quizDataArray = [];
  constructor(
    private rest: QuizfetchService,
    private route: ActivatedRoute,
    private router: Router,
    private fetchQues: FetchQuestionsService,
    private localStorageService: LocalStorageService,
    private dialog: MatDialog,
    private logger: LoggerService
  ) { }
  // dataSource = ELEMENT_DATA;
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');

    this.quizCode = id;
    const url = `kaliber-quiz-play/quizzes/${this.quizCode}`;
    this.rest.getdata(url).subscribe(data => {
      this.logger.log(JSON.stringify(data) + ' quizData');
      this.quizData = data;
      this.quiz = this.quizData.result;
      this.logger.log(JSON.stringify(this.quiz) + ' quizObject');
      this.allowWager = this.quiz.allowWager;
      this.confidenceWager = this.quiz.confidenceWager;
      this.hasData = true;
      if (this.quiz.concepts != null) {
        if (this.quiz.concepts.length > 0) {
          this.quiz.concepts = this.quiz.concepts.slice(0, 7);
      }
    }
      this.quiz.sections.forEach(section => {
        const url1 = `kaliber-quiz-play/quizzes/${this.quizCode}/sections/${section.sectionTitle}`;
        this.rest
          .getQuizSectionQuestion(url1)

          .subscribe(dt => {
            this.sections = dt;
            this.QuizSections.push(this.sections.result);
            // this.localStorageService.storeQuizOnLocalStorage(this.QuizSections);
            // tslint:disable-next-line: no-shadowed-variable
          }, error => {
            this.logger.log('Error occured', error);
          });
      });
      // tslint:disable-next-line: no-shadowed-variable
    }, error => {
      this.logger.log('Error occured', error);
    });
  }

  startQuiz(quiz) {
    // tslint:disable-next-line: prefer-const
    let quizDataArray = [this.QuizSections, quiz.maxDurationMinutes,
    this.allowWager, this.confidenceWager, this.quiz.title, this.quiz.totalQuestions];
    const url = `kaliber-quiz-play/quizzes/${this.quizCode}/startQuiz`;
    this.rest
      .startQuiz(url)
      .subscribe((data) => {
        const submissionId = data.result;
        quizDataArray.push(submissionId);
        this.logger.log('quizDataArray ', JSON.stringify(quizDataArray));
        this.fetchQues.sendArray(quizDataArray);
        this.router.navigate(['/quizplay', this.quizCode]);
        // tslint:disable-next-line: no-shadowed-variable
      }, error => {
        this.logger.log('Error occured', error);
      });
  }
  onCancel() {
    this.dialogRef = this.dialog.open(MatquizplaydialogComponent, {
      data: {
        message: 'Are you sure you want to cancel?',
        link: ['/dashboard']
      }
    });
  }
}
