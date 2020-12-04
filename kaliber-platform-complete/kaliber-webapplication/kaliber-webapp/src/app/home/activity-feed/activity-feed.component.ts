import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification.service';
import { Router } from '@angular/router';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-activity-feed',
  templateUrl: './activity-feed.component.html',
  styleUrls: ['./activity-feed.component.scss']
})
export class ActivityFeedComponent implements OnInit {
  panelOpenState = false;

  constructor(private notifService: NotificationService,
              private router: Router, private logger: LoggerService) { }

  xpandStatus1 = true;
  xpandStatus2 = true;
  xpandStatus3 = true;

  Following = [];
  Challenges = [];
  challengeData;
  followingFeed;
  step = 0;

  ngOnInit() {
    this.getUserFeed();
    this.getUserChallenges();
  }
  getUserFeed() {
    const url = `kaliber-user-management/notifications/following`;
    this.notifService.getUserNotifFeed(url).subscribe((data) => {
      this.followingFeed = data;
      this.Following = this.followingFeed.result;
      this.logger.log('following, ', this.Following);

    });

  }

  setStep(index: number) {
    this.step = index;
  }
  getUserChallenges() {
    const url = `kaliber-user-management/notifications`;
    this.notifService.getUserChallenges(url).subscribe((data) => {
      this.challengeData = data;
      this.Challenges = this.challengeData.result;
      this.logger.log('challenges & invites, ', this.Challenges);

    });
  }
  play(quizCode) {
    this.router.navigate(['/quizLobby', quizCode]);

  }
  clear(notifObj, index) {
    this.Challenges.slice(index, 1);
  }

}
