import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { Message } from '../../models/message';
import {CookieService} from 'ngx-cookie-service';
import {DatePipe} from '@angular/common';

declare var $: any;

@Component({
  selector: 'app-conversations',
  templateUrl: './conversations.component.html',
  styleUrls: ['./conversations.component.css']
})
export class ConversationsComponent implements OnInit {
  message: string;
  messages: Message[] = [];

  constructor(public cookieService: CookieService,
              private chatService: ChatService,
              private datepipe: DatePipe) {
  }

  sendMessage() {
    this.chatService.sendMessage(this.message);
    const messageObject = new Message(0, this.cookieService.get('UserID'), this.cookieService.get('UserFullName'),
      this.message, this.datepipe.transform(new Date(Date.now()), 'yyyy-MM-dd HH:mm:ss' ), this.chatService.roomID);
    this.messages.push(messageObject);
    $('div[id=chat]').animate({scrollTop: $('ul.chat').height()});
    this.message = '';
  }

  ngOnInit() {
    this.chatService.requestChatMessages().subscribe(messages => {
      this.messages = messages;
      setTimeout(() => { $('div[id=chat]').animate({scrollTop: $('ul.chat').height()}); }, 600);
      this.chatService
        .getMessages()
        .subscribe((messageObject: Message) => {
          this.messages.push(messageObject);
          $('div[id=chat]').animate({scrollTop: $('ul.chat').height()});
        });
    });
  }
}
