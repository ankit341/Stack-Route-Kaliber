import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { LoggerService } from '../services/logger.service';

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent implements OnInit {

  public userRole: any;
  public userProfile: any = {};
  userRoleStatus: boolean;
  @Output() closeSideNavigation = new EventEmitter();
  userAvatar: any;
  userProfileAvatarUrl: string;
  username: any;
  useremail: any;
  constructor(public router: Router, private userService: UserService,
              private logger: LoggerService) { }

  ngOnInit() { this.getUserRole(); }

  onToggleClose() {
    this.closeSideNavigation.emit();
  }
  getUserRole() {
    this.userService.getUserProfile().subscribe(response => {
      this.logger.log('response is' + JSON.stringify(response));
      this.userProfile = response;
      this.userAvatar = this.userProfile.avatarURL;
      this.userProfileAvatarUrl = 'url(' + this.userAvatar + ')';
      this.username = this.userProfile.name;
      this.useremail = this.userProfile.email;
      this.userRole = this.userProfile.userRole;
      this.logger.log('userRole is' + this.userRole);
      if (this.userRole === 'ADMIN') {
        this.userRoleStatus = true;
      } else {
        this.userRoleStatus = false;
      }
    });
  }
}
