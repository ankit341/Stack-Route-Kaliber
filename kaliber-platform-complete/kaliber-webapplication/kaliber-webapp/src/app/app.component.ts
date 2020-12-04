import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth-service.service';
import { PageScrollConfig } from 'ng2-page-scroll';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'kaliber-angular';
  // constructor(private authService: AuthService, private route: Router) { }
  constructor(public router: Router) {
    PageScrollConfig.defaultScrollOffset = 30;
  }
  ngOnInit() {
    //   this.authService.getIsAuthCorrect().subscribe((data) => {
    //     if (data) {
    //       this.route.navigate(['/dashboard'], { state: { userProfile: this.authService.currentUser } });
    //     } else {
    //       this.route.navigate(['/']);
    //     }
    //   });
  }
}
