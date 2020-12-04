import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { UserService } from '../services/user.service';
import { Route, Router } from '@angular/router';
import { LoggerService } from '../services/logger.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() SideNavigationToggle = new EventEmitter();

  userProfile: any = {};
  username: any;
  userAvatar: any;
  userProfileAvatarUrl: any;
  ngOnInit() { this.getUserProfile(); }

  constructor(
    public router: Router,
    private userService: UserService,
    private logger: LoggerService
    ) { }

  onToggleOpenSidenav() {
    this.SideNavigationToggle.emit();
  }
  getUserProfile() {
    this.userService.getUserProfile().subscribe(response => {
      this.logger.log('response is' + response);
      this.userProfile = response;
      this.userAvatar = this.userProfile.avatarURL;
      this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
      this.username = this.userProfile.name;
      this.logger.log('username is' + this.username);
    });
  }
  logout() {
    this.userService.logUserOut().subscribe(res => {
      this.logger.log('logout response', res);
    });
    this.router.navigate(['']);
  }

}
