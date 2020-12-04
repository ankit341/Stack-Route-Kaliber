import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormGroup, FormControl } from '@angular/forms';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent implements OnInit {
  // public usersToChild: any[];
  // public topicsToChild: any[];
  delete = 2;
  userProfile: any = {};
  userAvatar: any;
  userProfileAvatarUrl: any;
  removable = true;
  public userDetails: any;
  public errorMsg: any;
  userName: any;
  follower;
  following;
  userDetailsData: any;
  public followTopicsCount = 0;
  public followersCount = 0;
  public usersFollowingCount = 0;
  public followedTopics = [];

  topicsForm = new FormGroup({
    newTopic: new FormControl('')
  });
  followerDetails = [];
  followingDetails = [];
  constructor(private userService: UserService,
              private logger: LoggerService) { }

  ngOnInit() {
    this.getOneUserDetails();
    // this.toAddNewTopic();
  }
  // getAllUserDetails() {
  //   this.userService.getAll().subscribe(data => {
  //     this.userDetails = data;
  //     this.logger.log(this.userDetails);
  //     (error: any) => {
  //       this.errorMsg = error;
  //     };
  //   });
  // }
  getOneUserDetails() {
    this.userService.getUserProfile().subscribe(data => {
      this.userProfile = data;
      // console.log('CHECCKING FOR USERNAME--> ', this.userProfile)
     // this.logger.log('CHECCKING FOR USERNAME--> ', this.userProfile)
      this.userAvatar = this.userProfile.avatarURL;
      this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
      this.userName = this.userProfile.username;
      this.userDetails = data;
      this.logger.log('userDetails:: ', this.userDetails);
      if (this.userDetails.followedTopics != null) {
        this.followTopicsCount = this.userDetails.followedTopics.length;
      }
      if (this.userDetails.followers != null) {
        this.followersCount = this.userDetails.followers.length;
        this.userDetails.followers.forEach(element => {
          this.userService.getByUserName(element).subscribe((followedData) => {
            console.log('followers', followedData);
            this.follower = followedData;
            this.follower = this.follower.result;
            this.followerDetails.push({
              name: this.follower.name,
              imageURL: this.follower.avatarURL,
              points: this.follower.totalPoints,
              username: this.follower.username
            });
            console.log('followerObj', this.followerDetails);
          });

        });
      }
      if (this.userDetails.usersFollowing != null) {
        this.usersFollowingCount = this.userDetails.usersFollowing.length;
        this.userDetails.usersFollowing.forEach(element => {
          this.userService.getByUserName(element).subscribe((followingData) => {
            console.log('followers', followingData);
            this.following = followingData;
            this.following = this.following.result;
            this.followingDetails.push({
              name: this.following.name,
              imageURL: this.following.avatarURL,
              points: this.following.totalPoints,
              username: this.following.username
            });
            console.log('followerObj', this.followingDetails);
          });

        });

      }
      this.userName = this.userDetails.username;
      this.logger.log(this.userDetails);
    },
      (error: any) => {
        this.errorMsg = error;
      }
    );
  }

  toAddNewTopic() {
    // this.logger.log('Added topic is ', this.topicsForm.value.newTopic);
    if (this.userDetails.followedTopics == null) {
      this.userDetails.followedTopics = [];
    }
    this.followedTopics = this.userDetails.followedTopics;
    this.followedTopics.push(this.topicsForm.value.newTopic);
    // this.logger.log("checiing values: ", this.topicsForm.value.newTopic);
    // this.logger.log("all TOPICS+==== > ", this.followedTopics);
    this.userService.addNewTopic(this.userName, this.followedTopics)
      .subscribe(dt => {
        // this.logger.log("data---------> ", dt);
        this.followTopicsCount++;
      });
  }

  toRemoveTopic(topics) {
    // this.logger.log("topics--> ", topics);
    const selectedTopics = [topics];
    this.followedTopics = this.userDetails.followedTopics;
    const tp = [];
    this.userDetails.followedTopics.forEach((d, i) => {
      if (d !== topics) {
        tp.push(d);
      }
      if (i === this.userDetails.followedTopics.length - 1) {
        this.userDetails.followedTopics = tp;
        // this.logger.log("final topics:: ", this.userDetails.followedTopics);
      }
    });
    // this.logger.log("Followed topics is ++++++ ", this.followedTopics);
    this.userService.deleteFollowedTopic(this.userName, selectedTopics)
      .subscribe(dt => {
        this.followTopicsCount--;
      });
  }
}
