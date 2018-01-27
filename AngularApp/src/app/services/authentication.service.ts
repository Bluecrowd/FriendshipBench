import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { CookieService } from 'ngx-cookie-service';
import { Constants } from '../Constants';
import { Credentials } from '../models/credentials';
import { Token } from '../models/token';
import { AccountDetails } from '../models/accountDetails';
import {RegisterForm} from '../models/registerForm';

/** constant for defining the content type */
const httpCredentialOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    'Authorization': 'Basic ' + btoa('OauthClient:OauthSuperSecret') })
};

@Injectable()
export class AuthenticationService {

  public httpTokenOptions;

  private oauthUrl = Constants.API_URL + 'oauth/token';  // URL to web api
  private accountDetailsUrl = Constants.API_URL + 'account/me';  // URL to web api
  private createHealhworkerUrl = Constants.API_URL + 'healthworkers/register';  // URL to web api

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
    ) { }

  /** POST: login to the server */
  public login (credentials: Credentials) {
    credentials.grant_type = 'password';
    const httpBody = 'username=' + credentials.username +
                     '&password=' + credentials.password +
                     '&grant_type=' + credentials.grant_type;

    this.requestToken(httpBody).subscribe(token => {
      this.httpTokenOptions = {headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'bearer ' + token.access_token
        })
      };
      this.requestAccountDetails().subscribe(accountDetails => {
        this.saveCookies(accountDetails as AccountDetails, token);
      });
    });
  }

  public register (registerFrom: RegisterForm) {
    this.submitNewAccount(registerFrom as object).subscribe(response => {console.log(response);});
  }

  private submitNewAccount (registerForm: object): Observable<object> {
    return this.http.post(this.createHealhworkerUrl, registerForm, this.httpTokenOptions);
  }

  private requestToken (httpBody: string): Observable<Token> {
    return this.http.post<Token>(this.oauthUrl, httpBody, httpCredentialOptions);
  }

  private requestAccountDetails (): Observable<object> {
    return this.http.get<AccountDetails>(this.accountDetailsUrl, this.httpTokenOptions);
  }

  private saveCookies (accountDetails: AccountDetails, token: Token) {
    const expireDate = new Date(Date.now());
    expireDate.setHours( expireDate.getHours() + 1);

    let saveAccessToken = false;

    for (const role of accountDetails.roles) {
      switch (role.roleName){
        case 'ADMIN': {
          this.cookieService.set('UserRights', 'ADMIN', expireDate );
          saveAccessToken = true;
          break;
        }
        case 'CLIENT': {
          this.cookieService.set('UserRights', 'CLIENT', expireDate );
          saveAccessToken = true;
          break;
        }
        case 'HEALTHWORKER': {
          this.cookieService.set('UserRights', 'HEALTHWORKER', expireDate );
          saveAccessToken = true;
          break;
        }
      }
    }
    if (saveAccessToken) {
      this.cookieService.set('UserAccessToken', token.access_token, expireDate );
    }
  }

}
