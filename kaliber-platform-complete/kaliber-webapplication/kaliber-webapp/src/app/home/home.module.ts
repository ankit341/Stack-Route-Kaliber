import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { KaliberMaterialModule } from '../kaliber-material.module';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { TakenQuizesComponent } from './taken-quizes/taken-quizes.component';
import { CreatedQuizesComponent } from './created-quizes/created-quizes.component';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminquizbankComponent } from './adminquizbank/adminquizbank.component';
import { MatConfirmDialogComponent } from './mat-confirm-dialog/mat-confirm-dialog.component';
import { QuestionBankViewComponent } from './question-bank-view/question-bank-view.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { QuestionPreviewComponent } from '../create-quiz/question-preview/question-preview.component';
// import { QuestionBankViewComponent } from '../question-bank-view/question-bank-view.component';
import { NgxInfiniteScrollerModule } from 'ngx-infinite-scroller';
import { SidebarModule } from 'ng-sidebar';



// import { QuestionPreviewComponent } from '../create-quiz/question-preview/question-preview.component';
import { SnackbarComponentComponent } from '../create-quiz/snackbar-component/snackbar-component.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ImportQuestionDialogComponent } from './import-question-dialog/import-question-dialog.component';
import { InviteChallengeComponent } from './invite-challenge/invite-challenge.component';
import { ActivityFeedComponent } from './activity-feed/activity-feed.component';
@NgModule({
  declarations: [
    DashboardComponent,
    MyProfileComponent,
    TakenQuizesComponent,
    CreatedQuizesComponent,
    AdminquizbankComponent,
    MatConfirmDialogComponent,
    QuestionBankViewComponent,
    ImportQuestionDialogComponent,
    InviteChallengeComponent,
    ActivityFeedComponent,
  ],
  providers: [
    QuestionPreviewComponent,
    { provide: MatDialogRef },
    { provide: MAT_DIALOG_DATA, useValue: {} }
  ],

  entryComponents: [
    DashboardComponent,
    QuestionBankViewComponent,
    QuestionPreviewComponent,
    SnackbarComponentComponent,
    // QuestionPreviewComponent
    ImportQuestionDialogComponent,
    InviteChallengeComponent

  ],
  imports: [
    CommonModule,
    KaliberMaterialModule,
    AppRoutingModule,
    SharedModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    NgxInfiniteScrollerModule,
    MatSnackBarModule,
    SidebarModule.forRoot()
  ],
  exports: [
  ]
})

export class HomeModule { }
