import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Client} from '../models/client';
import {catchError, tap} from 'rxjs/operators';
import { Constants } from '../Constants';

import { HandleErrorService} from './handle-error.service';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class ClientsService {

  private clientsUrl = Constants.API_URL + 'clients';  // URL to web api
  private httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' })};

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService,
    private cookieService: CookieService
    ) { }

  /** GET clients from the server */
  getClients (): Observable<Client[]> {
    this.setHeaderOptions();
    return this.http.get<Client[]>(this.clientsUrl, this.httpOptions)
      .pipe(
        tap(clients => this.handleErrorService.log(`ClientService: fetched clients`)),
        catchError(this.handleErrorService.handleError('getClients', []))
      );
  }

  private setHeaderOptions () {
    if (this.cookieService.check('UserAccessToken')) {
      this.httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'bearer ' + this.cookieService.get('UserAccessToken')
        })};
    }
  }

}
