import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ChipComponent} from './chip/chip.component';
import {ProfileCardComponent} from './profile-card/profile-card.component';
import {KaliberMaterialModule } from '../kaliber-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ChipComponent,
    ProfileCardComponent,
  ],
  imports: [
    CommonModule,
    KaliberMaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule
  ]

})
export class UserProfileModule { }
