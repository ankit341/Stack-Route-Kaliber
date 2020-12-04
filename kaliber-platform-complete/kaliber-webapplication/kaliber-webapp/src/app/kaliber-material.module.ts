import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSliderModule } from '@angular/material/slider';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatChipsModule } from '@angular/material/chips';
import { MatStepperModule } from '@angular/material/stepper';
import {MatTableModule} from '@angular/material/table';
import { NgxInfiniteScrollerModule } from 'ngx-infinite-scroller';
import {MatDividerModule} from '@angular/material/divider';
import { NgCircleProgressModule } from 'ng-circle-progress';


import { AvatarModule } from 'ngx-avatar';
// import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import {
  MatBadgeModule,
  MatTabsModule,
  MatAutocompleteModule,
  MatFormFieldModule,
  MatRadioModule,
  MatDialogModule,
  MatGridListModule,
  MatExpansionModule,
  MatCheckboxModule,
  MatSnackBarModule,
  MatProgressSpinnerModule,

  // MatSlideToggleModule
  // MatDialog
} from '@angular/material';
@NgModule({
  imports: [
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatCardModule,
    MatBadgeModule,
    MatTabsModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSlideToggleModule,
    MatSliderModule,
    MatButtonToggleModule,
    ScrollingModule,
    MatSelectModule,
    MatInputModule,
    MatDialogModule,
    MatMenuModule,
    MatGridListModule,
    MatTooltipModule,
    MatStepperModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatTableModule,
    NgxInfiniteScrollerModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    AvatarModule,
    MatToolbarModule,

    NgCircleProgressModule.forRoot({})

    // InfiniteScrollerModule
    // MatSlideToggleModule
    // MatDialog
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatCardModule,
    MatBadgeModule,
    MatTabsModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSlideToggleModule,
    MatSliderModule,
    MatButtonToggleModule,
    ScrollingModule,
    MatSelectModule,
    MatInputModule,
    MatDialogModule,
    MatMenuModule,
    MatGridListModule,
    MatTooltipModule,
    MatStepperModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatTableModule,
    NgxInfiniteScrollerModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatToolbarModule
    // InfiniteScrollModule
    // MatSlideToggleModule
    // MatDialog
  ]
})
export class KaliberMaterialModule { }
