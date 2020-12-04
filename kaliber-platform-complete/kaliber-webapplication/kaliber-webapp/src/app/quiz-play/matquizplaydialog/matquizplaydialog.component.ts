import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-matquizplaydialog',
  templateUrl: './matquizplaydialog.component.html',
  styleUrls: ['./matquizplaydialog.component.scss']
})
export class MatquizplaydialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<MatquizplaydialogComponent>,
    private localStorageService: LocalStorageService,
    private router: Router,
    private logger: LoggerService
  ) { }

  ngOnInit() {

  }

  route() {
    this.router.navigate(this.data.link);
    this.localStorageService.removeQuizOnLocalStorage();
  }
  lobbyfunction() {
    this.router.navigate(['/dashboard']);
  }
}
