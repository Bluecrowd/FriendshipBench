import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Credentials} from '../models/credentials';
import {Constants} from '../Constants';
import {HttpHeaders, HttpClient} from '@angular/common/http';

/** constant for defining the content type */
// const httpOptions = {headers: new HttpHeaders({
// 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('OauthClient:OauthSuperSecret')})};

/** constant for defining the content type */
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AuthenticationService {

  private authUrl = Constants.API_URL + 'oauth/token';  // URL to web api

  constructor(
    private http: HttpClient,
  ) { }

  /** POST: login to the server */
  login (credentials: Credentials): Observable<Credentials> {
    credentials.grant_type = 'password';

    const body = 'username=' + credentials.username + '&password=' + credentials.password + '&grant_type=password';
    console.log(body);
    console.log(credentials.grant_type + credentials.password + credentials.username)

    /** btoa function is for base64 encoding*/
    let authdata = 'OauthClient:OauthSuperSecret';
    authdata = btoa(authdata);

    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    return this.http.post<Credentials>(this.authUrl, body);
  }

}
