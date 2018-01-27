import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { CookieService } from 'ngx-cookie-service';
import { Constants } from '../Constants';
import { Credentials } from '../models/credentials';
import { Token } from '../models/token';
import { AccountDetails } from '../models/accountDetails';
import {RoleName} from '../models/roleName';
import {forEach} from '@angular/router/src/utils/collection';
import {element} from 'protractor';

/** constant for defining the content type */
const httpCredentialOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    'Authorization': 'Basic ' + btoa('OauthClient:OauthSuperSecret') })
};

@Injectable()
export class AuthenticationService {

  public httpTokenOptions;
  // public accountDetails: AccountDetails;

  private oauthUrl = Constants.API_URL + 'oauth/token';  // URL to web api
  private accountDetailsUrl = Constants.API_URL + 'account/me';  // URL to web api

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

    return this.requestToken(httpBody).subscribe(token => {
      this.httpTokenOptions = {headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'bearer ' + token.access_token
        })
      };
      this.requestAccountDetails().subscribe(accountDetails => {
        this.saveCookies(accountDetails as AccountDetails);
        // this.accountDetails = accountDetails as AccountDetails;
        // console.log(this.accountDetails);
      });
    });
  }

  private requestToken (httpBody: string): Observable<Token> {
    return this.http.post<Token>(this.oauthUrl, httpBody, httpCredentialOptions);
  }

  private requestAccountDetails (): Observable<object> {
    return this.http.get<AccountDetails>(this.accountDetailsUrl, this.httpTokenOptions);
  }

  private saveCookies (accountDetails: AccountDetails) {
    const expireDate = new Date(Date.now());
    expireDate.setHours( expireDate.getHours() + 1);
    console.log(expireDate);
    console.log(accountDetails);

    for (let role of accountDetails.roles) {

      switch (role.roleName){
        case 'ADMIN': {
          this.cookieService.set('UserRights', 'ADMIN', expireDate );
          break;
        }
        case 'CLIENT': {
          this.cookieService.set('UserRights', 'CLIENT', expireDate );
          break;
        }
        case 'HEALTHWORKER': {
          this.cookieService.set('UserRights', 'HEALTHWORKER', expireDate );
          break;
        }

      }

    }
  }

}
