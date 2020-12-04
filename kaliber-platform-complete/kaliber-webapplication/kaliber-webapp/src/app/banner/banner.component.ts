import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.scss']
})
export class BannerComponent implements OnInit {


  banners = [];
  constructor( private dialog: MatDialog) {
  }

  ngOnInit() {

  }
  login() {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '500px',
      height: '300px'
    });
  }
}
