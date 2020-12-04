import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HomeModule } from './home/home.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// import { LoginPageComponent } from './login-page/login-page.component';
import { KaliberMaterialModule } from './kaliber-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';

import { BasicAuthInterceptorService } from './services/basic-auth-interceptor.service';
import { CookieService } from 'ngx-cookie-service';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { QuizPlayModule } from './quiz-play/quiz-play.module';
// import { NgCircleProgressModule } from 'ng-circle-progress';
// import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import { CreateQuizModule } from './create-quiz/create-quiz.module';
// import { TooltipModule } from 'ng2-tooltip-directive';
import { UserProfileModule } from './user-profile/user-profile.module';

import { QuizsubmissionadminviewComponent } from './home/quizsubmissionadminview/quizsubmissionadminview.component';
import { AdminquizbankComponent } from './home/adminquizbank/adminquizbank.component';
import { MatConfirmDialogComponent } from './home/mat-confirm-dialog/mat-confirm-dialog.component';
import { HeaderComponent } from './header/header.component';
import { SidenavListComponent } from './side-nav/sidenav-list.component';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { LocalStorageService } from './services/local-storage.service';
import { NgxInfiniteScrollerModule } from 'ngx-infinite-scroller';
// import { QuestionPreviewComponent } from './create-quiz/question-preview/question-preview.component';
// import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import 'hammerjs';

import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { BannerComponent } from './banner/banner.component';
import { FeaturesComponent } from './features/features.component';
import { GalleryComponent } from './gallery/gallery.component';
import { Ng2PageScrollModule } from 'ng2-page-scroll';

import { ProfileCardComponent } from './user-profile/profile-card/profile-card.component';
import 'hammerjs';

import { LoginComponent } from './login/login.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { PlayquizComponent } from './quiz-play/playquiz/playquiz.component';
import { LeaderBoardComponent } from './leader-board/leader-board.component';
import { LeaderboardService } from './services/leaderboard.service';
import {TermsModalComponent} from './terms-modal/terms-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidenavListComponent,
    QuizsubmissionadminviewComponent,
    // QuestionPreviewComponent,
    NavbarComponent,
    BannerComponent,
    FeaturesComponent,
    GalleryComponent,
    FooterComponent,
    LoginComponent,
    LeaderBoardComponent,
    TermsModalComponent
  ],
  imports: [

    CreateQuizModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    KaliberMaterialModule,
    MatNativeDateModule,
    FormsModule,
    SharedModule,
    HttpClientModule,
    FlexLayoutModule,
    QuizPlayModule,
    UserProfileModule,
    HomeModule,
    // tslint:disable-next-line: deprecation
    StorageServiceModule,
    // NgCircleProgressModule.forRoot({}),
    // TooltipModule,
    NgxInfiniteScrollerModule,

    // tslint:disable-next-line: deprecation
    Ng2PageScrollModule.forRoot(),
    NgCircleProgressModule.forRoot({
      radius: 100,
      outerStrokeWidth: 16,
      innerStrokeWidth: 8,
      outerStrokeColor: '#78C000',
      innerStrokeColor: '#C7E596',
      animationDuration: 300,
    })
  ],
  bootstrap: [AppComponent],
  exports: [
  ],
  providers: [LocalStorageService, {provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptorService, multi: true},
  CookieService, LeaderboardService],
  entryComponents: [LoginComponent, AdminquizbankComponent, MatConfirmDialogComponent, TermsModalComponent]
})

export class AppModule { }



