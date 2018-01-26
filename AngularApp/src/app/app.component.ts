import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Credentials} from './models/credentials';
import {Bench} from './models/bench';
import {AuthenticationService} from './services/authentication.service';

@Component({
  selector: 'body',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  title = 'app';

  credentials: Object = {};

  userLogged: boolean;
  userType: string;
  currentDate: Date;
  get diagnostic() { return JSON.stringify(this.credentials); }

  constructor(
    public cookieService: CookieService,
    private authenticationService: AuthenticationService,
    ) {}

  ngOnInit() {
    this.currentDate = new Date();
    this.userLogged = false;
    this.cookieService.set('UserType', 'Admin', this.currentDate.setHours(Date.now() + 1) );
    this.cookieService.deleteAll();
    this.userLogged = this.cookieService.check('UserType')
    console.log(this.userLogged)
    if (this.userLogged) {
      this.userType = this.cookieService.get('UserType');
    }
  }

  login(): void {
    if (this.credentials != null) {
      this.authenticationService.login(this.credentials as Credentials)
        .subscribe();
    }
  }
}
