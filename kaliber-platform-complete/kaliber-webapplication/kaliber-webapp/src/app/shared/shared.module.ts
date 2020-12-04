import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KaliberMaterialModule } from '../kaliber-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { CardsComponent } from './cards/cards.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReportQuizComponent } from './report-quiz/report-quiz.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    CardsComponent,
    ReportQuizComponent,
  ],
  entryComponents: [
    ReportQuizComponent,
    CardsComponent
  ],
  imports: [
    CommonModule,
    KaliberMaterialModule,
    AppRoutingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    CardsComponent,
  ]
})
export class SharedModule { }
