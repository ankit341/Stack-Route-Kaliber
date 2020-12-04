import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlayquizComponent } from './playquiz/playquiz.component';
import { TestwindowComponent } from './testwindow/testwindow.component';
import { QuizfetchService } from './../services/quizfetch.service';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { QuizsubmissionfeedbackComponent } from './quizsubmissionfeedback/quizsubmissionfeedback.component';
import { FetchQuestionsService } from '../services/fetch-questions.service';
import { QuizSubmissionService } from './../services/quiz-submission.service';
import { KaliberMaterialModule } from '../kaliber-material.module';
import { ProgressGraphComponent } from './progress-graph/progress-graph.component';
// import { ProgressChartComponent } from './progress-chart/progress-chart.component';
import { AppRoutingModule } from '../app-routing.module';
import { MarkdownModule, MarkedOptions } from 'ngx-markdown';
import { HttpClient } from '@angular/common/http';
import { QuizfeedbackquestioncardComponent } from './quizfeedbackquestioncard/quizfeedbackquestioncard.component';
import { MatquizplaydialogComponent } from './matquizplaydialog/matquizplaydialog.component';
import { AvatarModule } from 'ngx-avatar';

import { NgCircleProgressModule } from 'ng-circle-progress';


@NgModule({
  declarations: [
    PlayquizComponent,
    TestwindowComponent,
    QuizsubmissionfeedbackComponent,
    ProgressGraphComponent,
    // ProgressChartComponent,
    QuizfeedbackquestioncardComponent,
    MatquizplaydialogComponent
  ],
  entryComponents: [
    MatquizplaydialogComponent,
    PlayquizComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    KaliberMaterialModule,
    FlexLayoutModule,
    AppRoutingModule,
    AvatarModule,

    NgCircleProgressModule.forRoot({}),
    MarkdownModule.forRoot({
      loader: HttpClient,
      markedOptions: {
        provide: MarkedOptions,
        useValue: {
          gfm: true,
          tables: true,
          breaks: false,
          pedantic: false,
          sanitize: false,
          smartLists: true,
          smartypants: false,
        },
      },

    }),
 MarkdownModule.forRoot({ loader: HttpClient }),
    // NgCircleProgressModule.forRoot({
    //   // set defaults here
    //   radius: 100,
    //   outerStrokeWidth: 16,
    //   innerStrokeWidth: 8,
    //   outerStrokeColor: '#78C000',
    //   innerStrokeColor: '#C7E596',
    //   animationDuration: 300,
    // })
  ],
  exports: [
    TestwindowComponent,
    QuizsubmissionfeedbackComponent
  ],
  providers: [QuizfetchService, FetchQuestionsService, QuizSubmissionService]
})
export class QuizPlayModule {}
