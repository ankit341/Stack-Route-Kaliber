import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KaliberMaterialModule } from '../../app/kaliber-material.module';
import { CreateQuizComponent } from './create-quiz.component';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { PreviewComponent } from './preview/preview.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QuizSectionsComponent } from './quiz-sections/quiz-sections.component';
import { QuestionComponent } from './question/question.component';
import { OptionComponent } from './option/option.component';
import { HomeModule } from '../home/home.module';
import { NgMatSearchBarModule } from 'ng-mat-search-bar';
import { AddQuestionsComponent } from '../create-quiz/add-questions/add-questions.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { QuestionService } from '../services/question.service';
import { MarkdownModule, MarkedOptions } from 'ngx-markdown';
import { HttpClientModule, HttpClient } from '@angular/common/http';

import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';
import { QuestionPreviewComponent } from './question-preview/question-preview.component';
import { SnackbarComponentComponent } from './snackbar-component/snackbar-component.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CreateQuestionComponent } from '../create-quiz/create-question/create-question.component';
import { MatChipsModule } from '@angular/material/chips';
import { MultipleAnswerComponent } from './multiple-answer/multiple-answer.component';

@NgModule({
  declarations: [
    CreateQuizComponent,
    QuestionComponent,
    PreviewComponent,
    QuizSectionsComponent,
    OptionComponent,
    AddQuestionsComponent,
    QuestionPreviewComponent,
    SnackbarComponentComponent,
    CreateQuestionComponent,
    MultipleAnswerComponent
    // MatDialogModule


  ],
  providers: [
    { provide: MatDialogRef },
    { provide: MAT_DIALOG_DATA, useValue: {} },

  ],
  entryComponents: [
    PreviewComponent,
    AddQuestionsComponent,
    QuestionPreviewComponent,
    SnackbarComponentComponent,
    CreateQuestionComponent,
  ],

  imports: [
    CommonModule,
    KaliberMaterialModule,
    AppRoutingModule,
    SharedModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HomeModule,
    NgMatSearchBarModule,
    InfiniteScrollModule,
    MatChipsModule,
    // MatDialogModule,
    // MatDialogRef,


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

  ],
  exports: [
    HomeModule, QuestionPreviewComponent, QuestionComponent, CreateQuestionComponent,
  ]
})
export class CreateQuizModule { }
export class DemoMaterialModule { }
