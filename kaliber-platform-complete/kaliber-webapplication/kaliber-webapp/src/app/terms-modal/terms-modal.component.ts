import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../services/dialog.service';
// import { DialogData } from '../side-nav/sidenav-list.component';
import { UserService } from '../services/user.service';

export interface DialogData {
  termasAndCondition: string;
}
@Component({
  selector: 'app-terms-modal',
  templateUrl: './terms-modal.component.html',
  styleUrls: ['./terms-modal.component.scss'],
})
export class TermsModalComponent implements OnInit {
  disabledChecked = true;
  userProfile;
  changeCheck(event) {
    this.disabledChecked = !event.checked;
    this.userService.getUserProfile().subscribe((res) => {
      this.userProfile = res;
      this.userService.toCheckTerms(this.userProfile.username).subscribe((data) => {
        console.log(data);
      });
    });
  }

  constructor(public dialogRef: MatDialogRef<TermsModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData,
              private userService: UserService,
  ) { }

  ngOnInit() {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  Close() {
    this.dialogRef.close();
  }
}
