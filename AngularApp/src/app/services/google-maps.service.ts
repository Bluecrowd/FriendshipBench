import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import { Constants } from '../Constants';

import { HandleErrorService} from './handle-error.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class GoogleMapsService {

  private googleMapsUrl = Constants.API_URL + 'googleMaps';  // URL to web api

  constructor(private http: HttpClient,
              public handleErrorService: HandleErrorService,) {
  }

}
