import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import {Bench} from '../models/bench';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HandleErrorService} from './handle-error.service';
import {CookieService} from 'ngx-cookie-service';
import {Constants} from '../Constants';
import {Healthworker} from '../models/healthworker';

@Injectable()
export class HealthworkersService {

  private healthworkersUrl = Constants.API_URL + 'healthworkers';  // URL to web api
  private httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json'})};

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService,
    private cookieService: CookieService
  ) { }

  /** GET benches from the server */
  getHealthworkers(): Observable<Healthworker[]> {
    this.setHeaderOptions();
    return this.http.get<Healthworker[]>(this.healthworkersUrl, this.httpOptions)
      .pipe(
        tap(healthworkers => this.handleErrorService.log(`HealthworkersService: fetched healthworkers`)),
        catchError(this.handleErrorService.handleError('getHealthworkers', []))
      );
  }

  /** PUT: update the healthworker on the server */
  updateHealthworker (mapper: Object, id: number): Observable<any> {
    this.setHeaderOptions();
    return this.http.put(Constants.API_URL + 'account/approve/' + id, mapper, this.httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`updated healthworker id=${id}`)),
      catchError(this.handleErrorService.handleError<any>('updateHealthworker'))
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
