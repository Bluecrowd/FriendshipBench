import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'body',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  title = 'app';

  userLogged: boolean;
  userType: string;
  currentDate: Date;
  constructor(public cookieService: CookieService) {}

  ngOnInit() {
    this.currentDate = new Date();
    this.userLogged = false;
    this.cookieService.set('UserType', 'Admin', this.currentDate.setHours(Date.now() + 1) );
    // this.cookieService.deleteAll();
    this.userLogged = this.cookieService.check('UserType')
    console.log(this.userLogged)
    if (this.userLogged) {
      this.userType = this.cookieService.get('UserType');
    }
  }
}
