import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HandleErrorService} from './handle-error.service';
import {Constants} from '../Constants';
import {CookieService} from 'ngx-cookie-service';
import {Appointment} from '../models/appointment';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import {Healthworker} from '../models/healthworker';
import {User} from '../models/user';
import {AccountDetails} from '../models/accountDetails';
import {AppointmentInterface} from '../models/appointment-interface';
import {AddAppointment} from '../models/add-appointment';

@Injectable()
export class AppointmentsService {

  private appointmentsUrl = Constants.API_URL + 'appointments';  // URL to web api
  private httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
  private accountDetailsUrl = Constants.API_URL + 'account/me';  // URL to web api

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService,
    private cookieService: CookieService) {}

  /** GET appointments froms server*/
  getAppointments(): Observable<Appointment[]> {
    this.setHeaderOptions();

    return this.http.get<Appointment[]>(this.appointmentsUrl, this.httpOptions)
      .pipe(
        tap( appointments => this.handleErrorService.log(`AppointmentsService: fetched appointments`)),
        catchError(this.handleErrorService.handleError('getAppointments', []))
      );
  }

  /** POST: create an appointment with a client */
  addAppointment(appointment: Appointment): Observable<Appointment> {
    this.setHeaderOptions();
    return this.http.post<Appointment>(this.appointmentsUrl + '/', appointment, this.httpOptions)
      .pipe(
        tap((appointment1: Appointment) => this.handleErrorService.log(`created appointment`)),
        catchError(this.handleErrorService.handleError<Appointment>('addAppointment'))
      );
  }

  requestAccountDetails (): Observable<AccountDetails> {
    this.setHeaderOptions();
    return this.http.get<AccountDetails>(this.accountDetailsUrl, this.httpOptions);
  }

  cancelAppointment(id: number): Observable<any> {
    this.setHeaderOptions();

    return this.http.put(this.appointmentsUrl + '/' + id + '/cancel', {  }, this.httpOptions)
      .pipe(
        tap(_ => this.handleErrorService.log('caceled appointment')),
        catchError(this.handleErrorService.handleError<any>('cacelAppointmnet'))
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
