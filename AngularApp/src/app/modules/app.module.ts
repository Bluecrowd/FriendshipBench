import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { HttpClientModule} from '@angular/common/http';

import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from '../in-memory-data.service';


import { AppComponent } from '../app.component';
import { UserComponent } from '../components/user/user.component';
import { AppRoutingModule } from './/app-routing.module';
import { UsersComponent } from '../components/users/users.component';
import {UserService} from '../services/user.service';
import { MessagesComponent } from '../components/messages/messages.component';
import { MessageService } from '../services/message.service';
import { UserSearchComponent } from '../components/user-search/user-search.component';
import { ClientsComponent } from '../components/clients/clients.component';
import { ClientsService } from '../services/clients.service';
import { ClientComponent } from '../components/client/client.component';


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UsersComponent,
    MessagesComponent,
    UserSearchComponent,
    ClientsComponent,
    ClientComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false }
    )
  ],
  providers: [ UserService, MessageService, ClientsService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
