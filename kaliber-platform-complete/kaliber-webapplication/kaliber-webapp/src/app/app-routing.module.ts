import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './home/dashboard/dashboard.component';

import { CreateQuizComponent } from './create-quiz/create-quiz.component';
import { CreatedQuizesComponent } from './home/created-quizes/created-quizes.component';
import { TakenQuizesComponent } from './home/taken-quizes/taken-quizes.component';
import { TestwindowComponent } from './quiz-play/testwindow/testwindow.component';
import { QuizsubmissionfeedbackComponent } from './quiz-play/quizsubmissionfeedback/quizsubmissionfeedback.component';
import { ProgressGraphComponent } from './quiz-play/progress-graph/progress-graph.component';
import { AdminquizbankComponent } from './home/adminquizbank/adminquizbank.component';
// import { ProgressChartComponent } from './quiz-play/progress-chart/progress-chart.component';
import { PlayquizComponent } from './quiz-play/playquiz/playquiz.component';
import { QuestionComponent } from './create-quiz/question/question.component';
import { ProfileCardComponent } from './user-profile/profile-card/profile-card.component';
import { QuizsubmissionadminviewComponent } from './home/quizsubmissionadminview/quizsubmissionadminview.component';
import { QuestionBankViewComponent } from './home/question-bank-view/question-bank-view.component';
import { ConfirmationGuard } from './quiz-play/confirmation.guard';
import { CreateQuestionComponent } from './create-quiz/create-question/create-question.component';
import { LeaderBoardComponent } from './leader-board/leader-board.component';
import { ActivityFeedComponent } from './home/activity-feed/activity-feed.component';
import { MultipleAnswerComponent } from './create-quiz/multiple-answer/multiple-answer.component';


const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, pathMatch: 'full'},
  { path: 'createquiz', component: CreateQuizComponent, pathMatch: 'full' },
  { path: 'createquiz/:quizCode', component: CreateQuizComponent, pathMatch: 'full' },
  { path: 'createdquizes', component: CreatedQuizesComponent, pathMatch: 'full' },
  { path: 'takenquizes', component: TakenQuizesComponent, pathMatch: 'full' },
  { path: 'myprofile', component: ProfileCardComponent},
  { path: 'quizLobby/:id', component: PlayquizComponent, pathMatch: 'full' },
  { path: 'quizplay/:quizCode', component: TestwindowComponent },
  { path: 'progressgraph', component: ProgressGraphComponent },
  { path: 'quizSubmission/:submissionId', component: QuizsubmissionfeedbackComponent },
  { path: 'adminquizsubmission', component: QuizsubmissionadminviewComponent },
  { path: 'adminquizbank', component: AdminquizbankComponent },
  { path: 'quizSubmission', component: QuizsubmissionfeedbackComponent },
  // { path: 'progresschart', component: ProgressChartComponent},
  { path: 'createquestion', component: CreateQuestionComponent},
  { path: 'leaderboard/:quizCode', component: LeaderBoardComponent},
  { path: 'questionbank', component: QuestionBankViewComponent, pathMatch: 'full' },
  { path: 'createquestion/:questionId', component: QuestionComponent},
  { path: 'editquestion', component: QuestionComponent},
  { path: 'editquestion/:questionId', component: QuestionComponent},
  { path: 'activityfeed', component: ActivityFeedComponent},
  { path: 'editmultquestion', component: MultipleAnswerComponent},
 { path: 'editmultquestion/:questionId', component: MultipleAnswerComponent},

  // { path: 'createquestion/:questionId?showEdit=1', component: QuestionComponent},

];

@NgModule({
  // RouterModule.forRoot(routes, { useHash: true })
  // providers: [ConfirmationGuard],
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// canDeactivate: [ConfirmationGuard]
