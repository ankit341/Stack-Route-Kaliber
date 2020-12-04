import { Component, OnInit, HostListener } from '@angular/core';
import { UserService } from '../services/user.service';
import * as lodash from 'lodash';
import { LeaderboardService } from '../services/leaderboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoggerService } from '../services/logger.service';
export interface LeaderBoardQuizData {
  name: string;
  position: number;
  score: number;
  positionChange: number;
  rating: string;
  imageURL: any;
}
const ELEMENT_DATA: LeaderBoardQuizData[] = [
  { position: 1, name: 'Sourav', score: 85, positionChange: 1, rating: '5.0', imageURL: '' },
  { position: 2, name: 'Saif', score: 82, positionChange: 0, rating: '5.0', imageURL: '' },
  { position: 3, name: 'Nithin', score: 72, positionChange: 3, rating: '4.5', imageURL: '' },
  { position: 4, name: 'Gautham', score: 70, positionChange: -1, rating: '4.5', imageURL: '' },
  { position: 5, name: 'Soumik', score: 68, positionChange: 7, rating: '4.0', imageURL: '' },
  { position: 6, name: 'Saif Baig', score: 65, positionChange: -4, rating: '4.0', imageURL: '' },
  { position: 7, name: 'Shirisha', score: 61, positionChange: -5, rating: '4.0', imageURL: '' },
  { position: 8, name: 'Rahul K', score: 52, positionChange: 4, rating: '3.5', imageURL: '' },
  { position: 9, name: 'Manikanta', score: 48, positionChange: 0, rating: '3.0', imageURL: '' },
  { position: 10, name: 'Darshan', score: 36, positionChange: -9, rating: '2.8', imageURL: '' },
];
@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.scss']
})
export class LeaderBoardComponent implements OnInit {
  displayedColumns: string[] = ['position', 'name', 'rating', 'score', 'positionChange'];
  displayedColumnMobile: string[] = ['position', 'name', 'score'];
  // dataSource = ELEMENT_DATA;
  ELEMENT_LEADERBOARD = [];
  dataSource;
  public page = 0;
  public limit = 10;
  positionVariable: boolean;
  userProfileAvatarUrl: any;
  userName: any;
  userDesignation: any;
  userProfile: any = {};
  userAvatar: any;
  userInCard: any = {};
  quizLeaderBoardData = false;
  userPresence: boolean;
  userAbsence: boolean;
  userAvatarRank1;
  userAvatarRank2;
  userAvatarRank3;
  userRank1;
  userRank2;
  userRank3;
  quizCode;
  userRank2Boolean;
  userRank3Boolean;
  quizCreatedBy;
  quizDetails: any = {};
  constructor(private userService: UserService,
              private leaderBoardService: LeaderboardService,
              private route: ActivatedRoute,
              private router: Router,
              private logger: LoggerService) { }
  ngOnInit() {
    this.quizCode = this.route.snapshot.paramMap.get('quizCode');
    this.getQuizData();
  }
  display() {
    if (this.ELEMENT_LEADERBOARD.length === 0) {
      this.quizLeaderBoardData = false;
    } else {
      this.quizLeaderBoardData = true;
    }
  }
  getUserProfile() {
    this.userService.getUserProfile().subscribe(response => {
      this.logger.log('response is' + response);
      this.userProfile = response;
      this.userAvatar = this.userProfile.avatarURL;
      this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
      this.userName = this.userProfile.name;
      this.userInCard = lodash.find(this.ELEMENT_LEADERBOARD, ['name', this.userName]);
      this.logger.log('username is' + this.userName);
      if (this.userInCard === undefined) {
        this.userPresence = false;
        this.userAbsence = true;
      } else {
        this.userPresence = true;
        this.userAbsence = false;
      }
      this.logger.log('userInCard is' + JSON.stringify(this.userInCard));
    });
  }
  getQuizByQuizCode() {
    this.leaderBoardService.getQuizByQuizCode(this.quizCode).subscribe((data) => {
      this.quizDetails = data.result[0];
      this.userService.getByUserName(this.quizDetails.createdBy).subscribe(response => {
        this.userProfile = response;
        this.userProfile = this.userProfile.result;
        this.quizCreatedBy = this.userProfile.name;
      });
    });
  }
  routeToQuizLobby() {
    this.router.navigate(['/quizLobby', this.quizDetails.quizCode]);
  }
  getQuizData() {
    // const urlQuizCode = `kaliber-leaderboard/leaderboard/${quizId}`;
    this.leaderBoardService.getLeaderBoard(this.quizCode).subscribe((leaderboard: any) => {
      this.ELEMENT_LEADERBOARD = leaderboard.result;
      const modifiedData = this.ELEMENT_LEADERBOARD.map((data, i) => {
        const objToReturn = data;
        objToReturn.position = i + 1;
        return objToReturn;
      });
      this.ELEMENT_LEADERBOARD = modifiedData;
      this.dataSource = this.ELEMENT_LEADERBOARD;
      if (this.ELEMENT_LEADERBOARD.length === 0) {
        this.quizLeaderBoardData = false;
      } else {
        this.quizLeaderBoardData = true;
      }
      this.getUserProfile();
      this.getQuizByQuizCode();
      this.userRank1 = this.ELEMENT_LEADERBOARD[0];
      this.userRank2 = this.ELEMENT_LEADERBOARD[1];
      this.userRank3 = this.ELEMENT_LEADERBOARD[2];
      if (this.userRank2 === undefined) {
        this.userRank2Boolean = false;
      } else {
        this.userRank2Boolean = true;
      }
      if (this.userRank3 === undefined) {
        this.userRank3Boolean = false;
      } else {
        this.userRank3Boolean = true;
      }
      this.userAvatarRank1 = 'url(' + this.userRank1.userAvatar + ')';
      this.userAvatarRank2 = 'url(' + this.userRank2.userAvatar + ')';
      this.userAvatarRank3 = 'url(' + this.userRank3.userAvatar + ')';
    });
  }
  @HostListener('window:scroll', []) onWindowScroll() {
    this.scrollFunction();
  }
  scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
      document.getElementById('myBtn').style.display = 'block';
    } else {
      document.getElementById('myBtn').style.display = 'none';
    }
  }
  topFunction() {
    document.documentElement.scrollTop = 0;
  }
}
