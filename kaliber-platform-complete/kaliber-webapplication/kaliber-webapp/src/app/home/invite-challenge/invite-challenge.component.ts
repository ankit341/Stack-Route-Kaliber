import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { UserService } from 'src/app/services/user.service';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { NotificationService } from 'src/app/services/notification.service';
import { LoggerService } from 'src/app/services/logger.service';
import {MatSnackBar} from '@angular/material';
@Component({
  selector: 'app-invite-challenge',
  templateUrl: './invite-challenge.component.html',
  styleUrls: ['./invite-challenge.component.scss']
})
export class InviteChallengeComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private dialogRef: MatDialogRef<InviteChallengeComponent>,
              private userService: UserService,
              private quizService: QuizInventoryService,
              private notifService: NotificationService,
              private logger: LoggerService,
              private snackBar: MatSnackBar) { }
  value = this.data.value;
  quiz = this.data.quizObj;
  // userNameArray = [];
  // userAvatarArray = []
  userProfile;
  quizData;
  userObj;
  userAvatar;
  authoredBy;
  userName;
  FollowerDetails = [];
  userDetails;
  followers = [];
  errorMsg;
  selectedUser = [];
  ngOnInit() {
    this.logger.log(this.value);
    this.logger.log(this.quiz);
    // this.authoredBy = this.quiz.createdBy;
    // this.userService.getUserProfile().subscribe((res) => {
    //   this.userProfile = res;
    // });
    const url = `kaliber-quiz-inventory/quizzes?quizCode=${this.quiz.quizCode}`;
    // this.quizService.getQuiz(url).subscribe((data) => {
    //   this.quizData = data;
    //   this.authoredBy = this.quizData.result.createdBy;
    //   this.getUserDetails(this.authoredBy);
    // })
    // this.getUserDetails(this.authoredBy);
    this.getUserDetails();
  }
  getUserDetails() {
    this.userService.getUserProfile().subscribe(data => {
      this.userProfile = data;
      // this.logger.log('CHECCKING FOR USERNAME--> ', this.userProfile)
      this.userAvatar = this.userProfile.avatarURL;
      // this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
      this.userName = this.userProfile.username;
      this.userDetails = data;
      this.logger.log('userDetails:: ', this.userDetails);
      if (this.userDetails.followers != null) {
        this.followers = this.userDetails.followers;
        this.getAllUserDetails(this.followers);
      }
      this.userName = this.userDetails.username;
      this.logger.log(this.userDetails);
    },
      (error: any) => {
        this.errorMsg = error;
      }
    );
  }
  getAllUserDetails(followerArray) {
    if (followerArray != null) {
      followerArray.forEach(element => {
        this.userService.getByUserName(element).subscribe((data) => {
          this.userObj = data;
          this.logger.log('one user', this.userObj.result);
          this.FollowerDetails.push(this.userObj.result);
        });
      });
    }
  }
  sendInvitation() {
    // this.logger.log( 'uname and qcode', this.quiz.quizCode);
    const url = `kaliber-user-management/notifications`;
    this.logger.log('uname and qcode', this.quiz.quizCode);
    this.FollowerDetails.forEach(element => {
      if (element.checked === true) {
        this.selectedUser.push(element);
        const obj = {
          challengedBy: this.userProfile.username,
          quizCode: this.quiz.quizCode,
          challengedTo: element.username,
          typeValue: 'INVITE',
          statusValue: 'PENDING'
        };
        this.notifService.postNotification(url, obj).subscribe((d) => {
          this.logger.log('after posting ', d);
        });
      }
    });
    this.logger.log('sel', this.selectedUser);
    this.dialogRef.close();
    this.openSnackBar('Invited ', 'succesfully');
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }
  sendChallenge() {
    this.logger.log('uname and qcode', this.quiz.quizCode);
    this.FollowerDetails.forEach(element => {
      if (element.checked === true) {
        this.selectedUser.push(element);
        const obj = {
          challengedBy: this.userProfile.username,
          quizCode: this.quiz.quizCode,
          challengedTo: element.username,
          typeValue: 'CHALLENGE',
          statusValue: 'PENDING'
        };
        const url = `kaliber-user-management/notifications`;
        this.notifService.postNotification(url, obj).subscribe((d) => {
          this.logger.log('after posting ', d);
        });
      }
    });
    this.logger.log('sel', this.selectedUser);
    this.dialogRef.close();
    this.openSnackBar('Challenged ', 'succesfully');
  }
  Close() {
    this.dialogRef.close();
  }
  onChange(event, user) {
    if (event.checked === true) {
      user.checked = true;
    } else {
      user.checked = false;
    }
  }
}
