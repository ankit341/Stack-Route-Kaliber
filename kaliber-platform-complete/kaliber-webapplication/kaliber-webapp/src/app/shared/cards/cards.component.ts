import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { componentFactoryName } from '@angular/compiler';
import { DialogService } from 'src/app/services/dialog.service';
import { UserService } from 'src/app/services/user.service';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { InviteChallengeComponent } from '../../home/invite-challenge/invite-challenge.component';
import { MatDialog } from '@angular/material';
import { LeaderBoardComponent } from 'src/app/leader-board/leader-board.component';
import { LoggerService } from 'src/app/services/logger.service';
import { ReportQuizComponent } from '../report-quiz/report-quiz.component';
@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css']
})
export class CardsComponent implements OnInit {

  @Input() quiz;
  @Input() componentName;
  public scoreExists = false;
  public firstThreeAvatarIcon;
  public firstThreeConcepts;
  public fav = false;
  userArray = [];
  userData;
  userProfile;
  subjectArray = [];
  userProfileStart;
  avatarImageUrl;
  arrayOfURLs = [];
  arrayOfImageUrls = [];

  quizCode;
  report;
  constructor(private router: Router,
              private dialogService: DialogService,
              private userService: UserService,
              private quizSerivce: QuizInventoryService,
              private dialog: MatDialog,
              private logger: LoggerService

  ) { }

  ngOnInit() {
    if (this.quiz.maxScore != null) {
      this.scoreExists = true;
    }

    // // to show only 3 avatar icons on the dashboard card.
    // if (this.quiz.arrayOfImageUrls != null) {
    //   console.log(this.quiz.arrayOfImageUrls, '::::: -----> avatar urls array');
    //   console.log('LENGTH OF ::-->', this.quiz.arrayOfImageUrls.length);
    //   if (this.quiz.arrayOfImageUrls.length > 3) {
    //     this.firstThreeAvatarIcon = this.quiz.arrayOfImageUrls.slice(0, 3);
    //   }
    // }
    if (this.quiz.concepts != null) {
      if (this.quiz.concepts.length > 3) {
        this.firstThreeAvatarIcon = this.quiz.concepts.slice(0, 3);
      }
      if (this.quiz.concepts.length > 0) {
        this.firstThreeConcepts = this.quiz.concepts.slice(0, 3);
      }
    }

    if (this.quiz.concepts != null) {
      if (this.quiz.concepts.length > 0) {
        this.firstThreeConcepts = this.quiz.concepts.slice(0, 3);
    }

      this.userService.getUserProfile().subscribe((res) => {
      this.userProfileStart = res;
    });


      this.quiz.arrayOfImageUrls = this.arrayOfURLs;
      this.quiz.players.forEach( res1 => {
      this.userService.getByUserName(res1).subscribe((data) => {
        this.userData = data;
        this.userData = this.userData.result;
        console.log(this.userData);
        this.avatarImageUrl = this.userData.avatarURL;
        this.quiz.arrayOfImageUrls.push(this.avatarImageUrl);
        if (this.quiz.arrayOfImageUrls != null) {
          console.log('LENGTH OF quiz array::-->', this.quiz.arrayOfImageUrls.length);
          if (this.quiz.arrayOfImageUrls.length > 3) {
            this.quiz.firstThreeAvatarIcon = this.quiz.arrayOfImageUrls.slice(0, 3);
            console.log('first three avatar url:: -->', this.quiz.firstThreeAvatarIcon);
          }
        }
      });
    });
  }
}


    addToFavourite(quizCode) {
    if (this.fav === false) {
      this.fav = true;
    } else {
      this.fav = false;
    }
  }
  followUser(userName) {
    this.logger.log('uname', userName);
    this.userService.getUserProfile().subscribe((res) => {
      this.userProfile = res;
      this.userArray.push(userName);
      this.logger.log('usernnn', this.userArray);
      this.userService.toFollowUser(this.userProfile.username, this.userArray).subscribe((data) => {
        this.logger.log(data);
      });

    });
  }
  followTopics(subject) {
    this.logger.log(subject);
    this.subjectArray.push(subject);
    // conceptArray.forEach(element => {
    this.userService.addNewTopic(this.userProfileStart.username, this.subjectArray).subscribe((data) => {
        this.logger.log('Topic posted', data);
      });
    // });
  }
    onSelect() {
    this.router.navigate(['/quizLobby', this.quiz.quizCode]);
  }
  feedBack(submissionId) {
    this.logger.log('sub id ', submissionId);
    this.router.navigate(['/quizSubmissions', submissionId]);
  }
  onEditQuiz(quizCode: string) {
    // this.logger.log('Quiz to edit is---->', quiz);
    this.router.navigate(['/createquiz', quizCode]);
    this.logger.log('QuizCode of selected quiz is', quizCode);
  }
  onDelete(quiz) {
    this.logger.log('deleteQuiz ---->', quiz);
    this.dialogService.openConfirmationDialog('Are you sure want to archive this quiz?')
      .afterClosed().subscribe(res => {
        if (res) {
          const statusValue = 'CLOSE';
          const url = `kaliber-quiz-inventory/quizzes/${quiz.quizCode}/status/${statusValue}`;
          this.quizSerivce.archiveQuizzes(url).subscribe((data) => {
            this.logger.log('after patch', data);
            this.logger.warn('! Archived/Closed Succesfully');
            this.router.navigate(['/adminquizbank']);

          });
        }
      });

  }
    onChallenge(quiz) {
    const val = 'challenge';
    const dialogRef = this.dialog.open(InviteChallengeComponent, {
      width: '750px',
      height: '575px',
      data: { value: val, quizObj: this.quiz}
    });
  }

  onInvite(quiz) {
    this.logger.log('oninvite');
    const val = 'invite';
    const dialogRef1 = this.dialog.open(InviteChallengeComponent, {
      width: '750px',
      height: '575px',
      data: { value: val, quizObj: this.quiz}
    });
  }

  displayLeaderboard() {
    this.logger.log('inside displayLeaderbaord' + this.quiz.quizCode);
    this.router.navigate(['/leaderboard', this.quiz.quizCode]);

  }

  getReportCount(quiz) {
    this.quizSerivce.getQuiz(quiz.quizCode).subscribe(response => {
      this.report = response;
    });
  }


  reportQuiz(quizCode) {
    const dialogRef = this.dialog.open(ReportQuizComponent, {
      width: '500px',
      // height: '500px',
      data: {
        quizCodeVar: quizCode
      }
    });
    return quizCode;
  }



}
