import { Injectable, NgZone } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Bench} from '../models/bench';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Constants} from '../Constants';
import {catchError, tap} from 'rxjs/operators';
import { MapsAPILoader } from "@agm/core";

import { HandleErrorService} from './handle-error.service';
import { CookieService } from 'ngx-cookie-service';
import { GoogleMapsAPIWrapper  } from "@agm/core";

declare var google: any;

@Injectable()
export class BenchesService extends GoogleMapsAPIWrapper {

  private benchesUrl = Constants.API_URL + 'benches';  // URL to web api
  private httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' })};


  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService,
    private cookieService: CookieService,
    private __loader: MapsAPILoader, private __zone: NgZone) {super(__loader, __zone);}

  /** GET benches from the server */
  getBenches(): Observable<Bench[]> {
    this.setHeaderOptions();
    return this.http.get<Bench[]>(this.benchesUrl, this.httpOptions)
      .pipe(
        tap(benches => this.handleErrorService.log(`BenchesService: fetched benches`)),
        catchError(this.handleErrorService.handleError('getBenches', []))
      );
  }

  /** PUT: update the bench on the server */
  updateBench (bench: Bench): Observable<any> {
    this.setHeaderOptions();
    return this.http.put(this.benchesUrl + '/' + bench.id, bench, this.httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`updated bench id=${bench.id}`)),
      catchError(this.handleErrorService.handleError<any>('updateBench'))
    );
  }

  /** POST: add a new bench to the server */
  addBench (bench: Bench): Observable<Bench> {
    this.setHeaderOptions();
    return this.http.post<Bench>(this.benchesUrl + '/', bench, this.httpOptions).pipe(
      tap((bench1: Bench) => this.handleErrorService.log(`added bench`)),
      catchError(this.handleErrorService.handleError<Bench>('addBench'))
    );
  }

  /** DELETE: delete the bench from the server */
  deleteBench ( bench: Bench | number): Observable<Bench> {
    const id = typeof bench === 'number' ? bench : bench.id;
    const url = `${this.benchesUrl}/${id}`;
    this.setHeaderOptions();

    return this.http.delete<Bench>(url, this.httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`deleted bench id=${id}`)),
      catchError(this.handleErrorService.handleError<Bench>('deletedBench'))
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

