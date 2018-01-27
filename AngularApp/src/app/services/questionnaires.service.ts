import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import {Questionnaire} from '../models/questionnaire';

import { HttpClient, HttpHeaders} from '@angular/common/http';
import {Constants} from '../Constants';
import {HandleErrorService} from './handle-error.service';

/** constant for defining the content type */
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class QuestionnairesService {

  private questionnairesUrl = Constants.API_URL + 'questionnaires';  // URL to web api

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService) { }

  /** GET questionnaires from the server */
  getQuestionnaires(): Observable<Questionnaire[]> {
    return this.http.get<Questionnaire[]>(this.questionnairesUrl, {responseType: 'json'})
      .pipe(
        tap(questionnaires => this.handleErrorService.log(`QuestionnairesService: fetched questionnaires`)),
        catchError(this.handleErrorService.handleError('getQuestionnaires', []))
      );
  }

  /** GET questionnaire by id. Will 404 if id not found */
  getQuestionnaire(id: number): Observable<Questionnaire> {
    const url = `${this.questionnairesUrl}/${id}`;
    return this.http.get<Questionnaire>(this.questionnairesUrl, {responseType: 'json'})
      .pipe(
        tap( _ => this.handleErrorService.log(`QuestionnairesService: fetched questionnaire id=${id}`)),
        catchError(this.handleErrorService.handleError<Questionnaire>('getQuestionnaireById'))
      );
  }

  /** PUT questionnaire for manually setting a red flag*/
  toggleRedFlag(questionnaire: Questionnaire): Observable<Questionnaire> {
    questionnaire.redFlag = !questionnaire.redFlag;
    return this.http.put(this.questionnairesUrl + '/' + questionnaire.id, questionnaire, httpOptions)
      .pipe(
        tap( _ => this.handleErrorService.log(`toggled red flag questionnaire id=${questionnaire.id}`)),
        catchError(this.handleErrorService.handleError<any>('setRedFlag'))
      );
  }

}
