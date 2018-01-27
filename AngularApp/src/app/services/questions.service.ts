import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Constants} from '../Constants';
import {HandleErrorService} from './handle-error.service';
import {Observable} from 'rxjs/Observable';
import {Question} from '../models/question';
import {catchError, tap} from 'rxjs/operators';
import {Bench} from '../models/bench';

/** constant for defining the content type */
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable()
export class QuestionsService {

  private questionsUrl = Constants.API_URL + 'questions'; // URL to web api

  constructor(
    private http: HttpClient,
    public handleErrorService: HandleErrorService
  ) { }

  /** GET all questions */
  getQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(this.questionsUrl, {responseType: 'json'})
      .pipe(
        tap( questions => this.handleErrorService.log(`QuestionsService: fetched questions`)),
        catchError(this.handleErrorService.handleError('getQuestions', []))
      );
  }

  /** POST: add a new question to the server */
  addQuestion (question: Question): Observable<Question> {
    return this.http.post<Question>(this.questionsUrl + '/', question, httpOptions).pipe(
      tap((question1: Question) => this.handleErrorService.log(`added question`)),
      catchError(this.handleErrorService.handleError<Question>('addQuestion'))
    );
  }

  /** PUT: update the Question on the server */
  updateQuestion (question: Question): Observable<Question> {
    console.log('test');
    return this.http.put(this.questionsUrl + '/' + question.id, question, httpOptions).pipe(
      tap(_ => this.handleErrorService.log(`updated question id=${question.id}`)),
      catchError(this.handleErrorService.handleError<any>('updateQuestion'))
    );
  }

}
