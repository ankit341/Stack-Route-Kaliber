
// import { Component, OnInit, Input } from '@angular/core';
// import { TakenquizService } from 'src/app/services/takenquiz.service';
// import { Router } from '@angular/router';
// import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
// import { QuizSubmissionService } from 'src/app/services/quiz-submission.service';
// import * as moment from 'moment';
// import { element } from 'protractor';
// import { Observable } from 'rxjs';
// import { UserService } from 'src/app/services/user.service';
// import { LoggerService } from 'src/app/services/logger.service';
// @Component({
//   selector: 'app-taken-quizes',
//   templateUrl: './taken-quizes.component.html',
//   styleUrls: ['./taken-quizes.component.css']
// })
// export class TakenQuizesComponent implements OnInit {
//   @Input() quiz;
//   takenQuizzes = 'takenQuizzes';
//   public store: any;
//   public takenQuizData = [];
//   public quizzes = [];
//   public quizzesTakenData: any;
//   public getQuizdetails: any;
//   public errorMsg = '';
//   public quizCodeColln = [];
//   public confidenceWager;
//   public answer;
//   public quizsubData: any;
//   public page = 0;
//   public limit = 10;
//   public quizScrollData;
//   public quizzesNewData;
//   result: any;
//   public request$: Observable<any>;
//   public totalQuizSubmissionCount;
//   public userProfileData: any = {};
//   public userAvatar: any;
//   userName: any;
//   uName = '';
//   storeColln = [];
//   public loaded = false;
//   public firstThreeAvatarIcon;
//   public firstThreeConcepts;
//   public unloaded = false;
//   count: number;
//   loadedScroll: boolean;
//   name: any;
//   // unloaded: boolean;
//   constructor(
//     private service: TakenquizService,
//     private router: Router,
//     private quizInventoryService: QuizInventoryService,
//     private quizSubmission: QuizSubmissionService,
//     private userService: UserService,
//     private logger: LoggerService
//   ) { }
//   ngOnInit() {
//     if (this.uName !== null) {
//       this.service.getTakenQuizData(`kaliber-quiz-play/quizSubmissions/user?page=${this.page}&limit=${this.limit}`).subscribe((res) => {
//         // this.loaded = true;
//         this.quizzesTakenData = res;
//         this.quizzes = this.quizzesTakenData.result;
//         this.count = this.quizzesTakenData.count;
//         this.logger.log('MAPPING DATA-------> ', this.quizzes);
//         // tslint:disable-next-line: no-shadowed-variable
//         if (this.quizzes) {
//           // tslint:disable-next-line: no-shadowed-variable
//           this.quizzes = this.quizzesTakenData.result.map(element => {
//             this.logger.log('element.createdOn is', element.quizEndTime);
//             this.logger.log('Moment createdOn is -------->', moment().startOf('day').fromNow());
//             element.createdBy = moment(element.quizEndTime).startOf('day').fromNow();        // 17 hours ago
//             return element;
//           });
//           this.quizzes.forEach((dt, index) => {
//             this.logger.log('Dt data ---->', dt, 'dt.quizCode--------->', dt.quizCode);
//             this.quizCodeColln.push(dt.quizCode);
//             if (index === this.quizzes.length - 1) {
//               this.logger.log('quiz code array ------------------>', this.quizCodeColln);
//             }
//           });
//           // tslint:disable-next-line: no-shadowed-variable
//           this.quizzes.forEach((element, ky) => {
//             // const ele = element;
//             // this.loaded = true;
//             this.logger.log('ele', element);
//             this.quizInventoryService.putQuiz(`kaliber-quiz-inventory/quizzes?quizCode=${element.quizCode}`).subscribe(response => {
//               this.getQuizdetails = response;
//               this.logger.log('data------------------> ', response);
//               this.store = this.getQuizdetails.result[0];
//               element.concepts = this.store.concepts;
//               element.title = this.store.title;
//               element.description = this.store.description;
//               element.maxScore = this.store.maxScore;
//               element.maxDurationMinutes = this.store.maxDurationMinutes;
//               element.createdBy = this.store.createdBy;
//               element.createDate = this.store.createDate;
//               element.statusvalue = this.store.statusvalue;
//               this.storeColln.push(element);
//               // if (ky === this.quizzes.length - 1) {
//               //   this.logger.log('*************LOGIC IMPLMEENTED-----> ', this.storeColln);
//               // }
//               // this.logger.log('final store---------------------> ', this.store);
//               // this.logger.log('taken quizzes----------------->', this.quizzes);
//               // tslint:disable-next-line: no-shadowed-variable
//               this.quizzes.forEach(element => {
//                 // tslint:disable-next-line: no-shadowed-variable
//                 this.userService.getUserProfile().subscribe(userResponse => {
//                   this.userProfileData = userResponse;
//                   this.userProfileData = this.userProfileData.result;
//                   // this.logger.log(this.userProfile);
//                   // element.name = this.userProfileData.name;
//                 });
//                 this.addConcept();
//               });
//               // this.addConcept();
//             },
//             );
//           },
//             this.loaded = true
//           );
//         } else {
//           // this.loaded = true;
//           this.unloaded = true;
//         }
//       });
//     }
//   }


//   public onScroll(): void {
//     this.logger.log('INSIDE THE SCOLLER ');
//     this.page += 1;
//     if (this.count > this.quizzes.length) {
//       this.loadedScroll = false;
//       const url = `kaliber-quiz-play/quizSubmissions/user?page=${this.page}&limit=${this.limit}`;
//       this.service.getTakenQuizData(url).subscribe((scrollData) => {
//         this.logger.log('<-------------------Inside the page 1 ---------------------------->');
//         this.quizScrollData = scrollData;
//         this.quizzesNewData = this.quizScrollData.result;
//         this.logger.log('new data ----------------------------->', this.quizzesNewData);
//         this.quizzesNewData.forEach((scrollElement, i) => {
//           this.quizCodeColln.push(scrollElement.quizCode);
//           if (scrollElement.quizCode) {
//             this.quizInventoryService.putQuiz(`kaliber-quiz-inventory/quizzes?quizCode=${scrollElement.quizCode}`)
//               .subscribe((scrollResponse) => {
//                 this.getQuizdetails = scrollResponse;
//                 this.loadedScroll = true;
//                 this.store = this.getQuizdetails.result[0];
//                 // tslint:disable-next-line: no-string-literal
//                 scrollElement.concepts = this.store.concepts.name;
//                 // tslint:disable-next-line: no-string-literal
//                 scrollElement.title = this.store.title;
//                 // tslint:disable-next-line: no-string-literal
//                 scrollElement.description = this.store.description;
//                 // tslint:disable-next-line: no-string-literal
//                 scrollElement.maxScore = this.store.maxScore;
//                 this.quizzes.push(scrollElement);
//                 if (i === this.quizzesNewData.length - 1) {
//                   this.logger.log('new quiz obj with paginated----------------->', this.quizzes);
//                 }
//               });
//           }
//         });
//         this.addConcept();
//         this.logger.log('new1234 quiz obj with paginated----------------->', this.quizzesNewData);
//       });
//     }
//   }
//   onSelect(quizCode) {
//     this.router.navigate(['/quizLobby', quizCode]);
//   }
//   addConcept() {
//     // tslint:disable-next-line: no-shadowed-variable
//     this.quizzes.forEach(element => {
//       this.logger.log(element);
//       element.createDate = moment(element.quizEndTime).fromNow();
//       if (element.concepts != null) {
//         if (element.concepts.length > 3) {
//           element.firstThreeAvatarIcon = element.concepts.slice(0, 3);
//         }
//         if (element.concepts.length > 0) {
//           element.firstThreeConcepts = element.concepts.slice(0, 3);
//         }
//       }
//     });
//     this.logger.log(this.quizzes);
//   }
// }




import { Component, OnInit, Input } from '@angular/core';
import { TakenquizService } from 'src/app/services/takenquiz.service';
import { Router } from '@angular/router';
import { QuizInventoryService } from 'src/app/services/quiz-inventory.service';
import { QuizSubmissionService } from 'src/app/services/quiz-submission.service';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import * as lodash from 'lodash';
import { LoggerService } from 'src/app/services/logger.service';
@Component({
  selector: 'app-taken-quizes',
  templateUrl: './taken-quizes.component.html',
  styleUrls: ['./taken-quizes.component.css']
})
export class TakenQuizesComponent implements OnInit {
  @Input() quiz;
  takenQuizzes = 'takenQuizzes';
  public store: any;
  public takenQuizData = [];
  public quizzes = [];
  public quizzesTakenData: any;
  public getQuizdetails: any;
  public errorMsg = '';
  public quizCodeColln = [];
  public confidenceWager;
  public answer;
  quizzesArray = [];
  public quizsubData: any;
  public page = 0;
  public limit = 10;
  public quizScrollData;
  public quizzesNewData;
  result: any;
  public request$: Observable<any>;
  public totalQuizSubmissionCount;
  public userProfileData: any = {};
  public userAvatar: any;
  userName: any;
  uName = '';
  storeColln = [];
  public loaded = false;
  public firstThreeAvatarIcon;
  public firstThreeConcepts;
  public unloaded = false;
  count: number;
  loadedScroll: boolean;
  name: any;
  // unloaded: boolean;
  constructor(
    private service: TakenquizService,
    private router: Router,
    private quizInventoryService: QuizInventoryService,
    private quizSubmission: QuizSubmissionService,
    private userService: UserService,
    private logger: LoggerService
  ) { }
  ngOnInit() {
    this.service.getTakenQuizData(`kaliber-quiz-play/quizSubmissions/user?page=${this.page}&limit=${this.limit}`).subscribe(res => {
      this.quizzesTakenData = res;
      this.logger.log('quizzestaken', this.quizzesTakenData);
      this.quizzes = this.quizzesTakenData.result;
      this.count = this.quizzesTakenData.count || 0;
      this.logger.log('count is', this.count);
      this.loaded = true;
      this.quizzes = this.quizzesTakenData.result.map(element => {
        element.createDate = moment(element.quizEndTime).startOf('day').fromNow();        // 17 hours ago
        return element;
      });
      this.quizzes.forEach(element => {
        this.userService.getUserProfile().subscribe(userRes => {
          this.userProfileData = userRes;
          element.name = this.userProfileData.name;
        }); // end of getuserprofile data subscribe
      }); // end of for each for user profile data
      this.quizzes.forEach((dt, index) => {
        this.quizCodeColln.push(dt.quizCode);
        if (index === this.quizzes.length - 1) {
        } // if condition ends here
      }); // for condition ends here for getting quizcode collection
      this.quizzes.forEach((element, key) => {
        this.quizInventoryService.putQuiz(`kaliber-quiz-inventory/quizzes?quizCode=${element.quizCode}`).subscribe(response => {
          this.getQuizdetails = response;
          this.loaded = true;
          this.store = this.getQuizdetails.result[0];
          element.concepts = this.store.concepts;
          element.title = this.store.title;
          element.description = this.store.description;
          element.maxScore = this.store.maxScore;
          element.maxDurationMinutes = this.store.maxDurationMinutes;
          element.createdBy = this.store.createdBy;
          element.createDate = this.store.createDate;
          element.statusvalue = this.store.statusvalue;
          element.firstThreeConcepts = this.store.firstThreeConcepts;
          if (key === this.quizzes.length - 1) {
          }
          this.addConcept();
          // tslint:disable-next-line: no-shadowed-variable
        }); // quiz inventory subscribe ends herea
      }); // for each ends for quiz inventory service
    }); // get quiz submission subscribe ends here
  }
  public onScroll(): void {
    this.page += 1;
    if (this.count > this.quizzes.length) {
      this.loadedScroll = false;
      const url = `kaliber-quiz-play/quizSubmissions/user?page=${this.page}&limit=${this.limit}`;
      this.service.getTakenQuizData(url).subscribe((scrollData) => {
        this.loadedScroll = true;
        // this.quizScrollData = scrollData;
        this.quizzesTakenData = scrollData;
        this.quizzesArray = this.quizzesTakenData.result;
        this.quizzes.forEach(element => {
          this.userService.getUserProfile().subscribe(userRes => {
            this.userProfileData = userRes;
            element.name = this.userProfileData.name;
          }); // end of getuserprofile data subscribe
        }); // end of for each for user profile data
        this.quizzesArray.forEach((element, i) => {
          this.quizCodeColln.push(element.quizCode);
          // if (scrollElement.quizCode) {
          this.quizInventoryService.putQuiz(`kaliber-quiz-inventory/quizzes?quizCode=${element.quizCode}`).subscribe((scrollResponse) => {
            this.getQuizdetails = scrollResponse;
            this.loadedScroll = true;
            this.store = this.getQuizdetails.result[0];
            element.concepts = this.store.concepts;
            element.title = this.store.title;
            element.description = this.store.description;
            element.maxScore = this.store.maxScore;
            element.maxDurationMinutes = this.store.maxDurationMinutes;
            element.createdBy = this.store.createdBy;
            element.createDate = this.store.createDate;
            element.statusvalue = this.store.statusvalue;
            element.firstThreeConcepts = this.store.firstThreeConcepts;
            this.quizzes.push(element);
            if (i === this.quizzes.length - 1) {
            }
            this.addConcept();
          });
          // } // if condition ends here which need to be deleted
        });
        // this.addConcept();
      });
    }
  }
  onSelect(quizCode) {
    this.router.navigate(['/quizLobby', quizCode]);
  }
  addConcept() {
    // tslint:disable-next-line: no-shadowed-variable
    this.quizzes.forEach(element => {
      element.createDate = moment(element.quizEndTime).fromNow();
      if (element.concepts != null) {
        if (element.concepts.length > 3) {
          element.firstThreeAvatarIcon = element.concepts.slice(0, 3);
        }
        if (element.concepts.length > 0) {
          element.firstThreeConcepts = element.concepts.slice(0, 3);
        }
      }
    });
  }
}
