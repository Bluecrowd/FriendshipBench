import { Injectable } from '@angular/core';
import * as io from 'socket.io-client';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Constants} from '../Constants';
import {CookieService} from 'ngx-cookie-service';
import {Message} from '../models/message';

@Injectable()
export class ChatService {
  private url = 'https://hpa-chat.figueus.com';
  private socket;
  private chatUrl = Constants.API_URL + 'messages';  // URL to web api
  private httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
  public roomID = '0';

  constructor (
    private datepipe: DatePipe,
    private http: HttpClient,
    private cookieService: CookieService) {
    this.socket = io(this.url);
    // this.socket.emit('join room', '32');
    this.joinChat('3');
  }

  public joinChat (toClientID: string) {
    this.roomID = toClientID + this.cookieService.get('UserID');
    this.socket.emit('join room', this.roomID);
  }

  public sendMessage(message) {
    const currentTime: Date = new Date(Date.now());
    const currentTimeFormat = this.datepipe.transform(currentTime, 'yyyy-MM-dd hh:mm:ss');
    this.socket.emit('new message', this.cookieService.get('UserID'),
      this.cookieService.get('UserFullName'), message, currentTimeFormat, this.roomID );
  }

  public getChatMessages () {

  }

  public requestChatMessages (): Observable<Message[]> {
    this.setHeaderOptions();
    return this.http.get<Message[]>(this.chatUrl + '/' + this.roomID, this.httpOptions);
  }

  public getMessages = () => {
    return Observable.create((observer) => {
      this.socket.on('new message', (user, name, message, timestamp) => {
        const messageObject = new Message(0, user, name, message, timestamp, this.roomID );
        observer.next(messageObject);
      });
    });
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
