import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Credentials} from './models/credentials';
import {Bench} from './models/bench';
import {AuthenticationService} from './services/authentication.service';
import {RegisterForm} from './models/registerForm';


@Component({
  selector: 'body',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  title = 'app';
  public authorizationPanel = 'LOGIN';
  public credentials: Object = {};
  public registerForm: RegisterForm = new RegisterForm();

  get diagnostic() { return JSON.stringify(this.credentials); }

  constructor(
    public cookieService: CookieService,
    private authenticationService: AuthenticationService
    ) {}

  ngOnInit() {
    // this.cookieService.deleteAll();
  }

  login(): void {
    if (this.credentials != null) {
      this.authenticationService.login(this.credentials as Credentials);
      this.credentials = {};
    }
  }

  register(): void {
    if (this.registerForm != null) {
      this.authenticationService.register(this.registerForm);
    }
  }

  logout(): void {
    this.cookieService.deleteAll();
    location.assign('/');
  }

  switchPage(page: string) {
    this.authorizationPanel = page;
  }
}
