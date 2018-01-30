import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import {Questionnaire} from '../models/questionnaire';

import { HttpClient, HttpHeaders} from '@angular/common/http';
import {Constants} from '../Constants';
import {HandleErrorService} from './handle-error.service';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class QuestionnairesService {

  private questionnairesUrl = Constants.API_URL + 'questionnaires';  // URL to web api
  /** constant for defining the content type */
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService,
    private cookieService: CookieService) { }

  /** GET questionnaires from the server */
  getQuestionnaires(): Observable<Questionnaire[]> {
    this.setHeaderOptions();
    return this.http.get<Questionnaire[]>(this.questionnairesUrl, this.httpOptions)
      .pipe(
        tap(questionnaires => this.handleErrorService.log(`QuestionnairesService: fetched questionnaires`)),
        catchError(this.handleErrorService.handleError('getQuestionnaires', []))
      );
  }

  /** GET questionnaire by id. Will 404 if id not found */
  getQuestionnaire(id: number): Observable<Questionnaire> {
    this.setHeaderOptions()
    const url = `${this.questionnairesUrl}/${id}`;
    return this.http.get<Questionnaire>(url, this.httpOptions)
      .pipe(
        tap( _ => this.handleErrorService.log(`QuestionnairesService: fetched questionnaire id=${id}`)),
        catchError(this.handleErrorService.handleError<Questionnaire>('getQuestionnaireById'))
      );
  }

  /** GET questionnaires by CLIENT id from the server */
  getAllQuestionnairesMyclientMade(id: number): Observable<Questionnaire[]> {
    this.setHeaderOptions();
    return this.http.get<Questionnaire[]>(this.questionnairesUrl + '/client/' + id, this.httpOptions)
      .pipe(
        tap(questionnaires => this.handleErrorService.log(`QuestionnairesService: fetched questionnaires by client id= ${id}`)),
        catchError(this.handleErrorService.handleError('getAllQuestionnairesMyclientMade', []))
      );
  }

  /** PUT questionnaire for manually setting a red flag*/
  toggleRedFlag(questionnaire: Questionnaire): Observable<Questionnaire> {
    this.setHeaderOptions()
    questionnaire.redflag = !questionnaire.redflag;
    return this.http.put(this.questionnairesUrl + '/' + questionnaire.id, questionnaire, this.httpOptions)
      .pipe(
        tap( _ => this.handleErrorService.log(`toggled red flag questionnaire id=${questionnaire.id}`)),
        catchError(this.handleErrorService.handleError<any>('setRedFlag'))
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
