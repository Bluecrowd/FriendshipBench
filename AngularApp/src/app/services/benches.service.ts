import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Bench} from '../models/bench';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Constants} from '../Constants';
import {catchError, tap} from 'rxjs/operators';

import { HandleErrorService} from './handle-error.service';

/** constant for defining the content type */
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class BenchesService {

  private benchesUrl = Constants.API_URL + 'benches';  // URL to web api

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService) { }

  /** GET benches from the server */
  getBenches(): Observable<Bench[]> {
    return this.http.get<Bench[]>(this.benchesUrl, {responseType: 'json'})
      .pipe(
        tap(benches => this.handleErrorService.log(`BenchesService: fetched benches`)),
        catchError(this.handleErrorService.handleError('getBenches', []))
      );
  }

  /** PUT: update the bench on the server */
  updateBench (bench: Bench): Observable<any> {
    return this.http.put(this.benchesUrl + '/' + bench.id, bench, httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`updated bench id=${bench.id}`)),
      catchError(this.handleErrorService.handleError<any>('updateBench'))
    );
  }

  /** POST: add a new bench to the server */
  addBench (bench: Bench): Observable<Bench> {
    return this.http.post<Bench>(this.benchesUrl + '/', bench, httpOptions).pipe(
      tap((bench1: Bench) => this.handleErrorService.log(`added bench`)),
      catchError(this.handleErrorService.handleError<Bench>('addBench'))
    );
  }

  /** DELETE: delete the bench from the server */
  deleteBench ( bench: Bench | number): Observable<Bench> {
    const id = typeof bench === 'number' ? bench : bench.id;
    const url = `${this.benchesUrl}/${id}`;

    return this.http.delete<Bench>(url, httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`deleted bench id=${id}`)),
      catchError(this.handleErrorService.handleError<Bench>('deletedBench'))
    );
  }
}
