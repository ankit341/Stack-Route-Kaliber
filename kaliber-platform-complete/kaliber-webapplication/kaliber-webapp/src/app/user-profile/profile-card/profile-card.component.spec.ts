import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileCardComponent } from './profile-card.component';
import { ChipComponent } from '../chip/chip.component';
import { KaliberMaterialModule } from 'src/app/kaliber-material.module';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MatInputModule } from '@angular/material';
import { HttpClientJsonpModule, HttpClientModule } from '@angular/common/http';

describe('ProfileCardComponent', () => {
  let component: ProfileCardComponent;
  let fixture: ComponentFixture<ProfileCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileCardComponent , ChipComponent ],
      imports: [KaliberMaterialModule , FormsModule , BrowserModule , MatInputModule , HttpClientModule]
    })
    .compileComponents();

  }));

  beforeEach(async () => {
    fixture = TestBed.createComponent(ProfileCardComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  // it('should create', async () => {
  //   expect(component).toBeTruthy();
  // });

  // it('should render title in h1 tag', async () => {
  //    // tslint:disable-next-line: no-shadowed-variable
  //    const fixture = TestBed.createComponent(ProfileCardComponent);
  //    fixture.detectChanges();
  //    const compiled = fixture.debugElement.nativeElement;
  //    expect (compiled.querySelector('h1').textContent).toContain('My Profile');

  // });

});
