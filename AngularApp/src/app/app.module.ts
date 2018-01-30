import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import {I1, I2} from './models/httpinterceptors';


import { AppComponent } from './app.component';
import { CookieService } from 'ngx-cookie-service';
import { UserComponent } from './components/user/user.component';
import { AppRoutingModule } from './modules/app-routing.module';
import { UsersComponent } from './components/users/users.component';
import {UserService} from './services/user.service';
import { MessagesComponent } from './components/messages/messages.component';
import { MessageService } from './services/message.service';
import { UserSearchComponent } from './components/user-search/user-search.component';
import { ClientsComponent } from './components/clients/clients.component';
import { ClientsService } from './services/clients.service';
import { ClientComponent } from './components/client/client.component';
import { BenchesComponent } from './components/benches/benches.component';
import { BenchComponent } from './components/bench/bench.component';
import { BenchesService } from './services/benches.service';
import { HandleErrorService } from './services/handle-error.service';
import { BenchFormComponent } from './components/bench-form/bench-form.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { AppointmentsService } from './services/appointments.service';
import { QuestionnairesComponent } from './components/questionnaires/questionnaires.component';
import { QuestionnairesService } from './services/questionnaires.service';
import { HealthworkersComponent } from './components/healthworkers/healthworkers.component';
import { HealthworkersService } from './services/healthworkers.service';
import { QuestionsComponent } from './components/questions/questions.component';
import { QuestionsService } from './services/questions.service';
import { QuestionFormComponent } from './components/question-form/question-form.component';
import { QuestionComponent } from './components/question/question.component';
import { AuthenticationService} from './services/authentication.service';
import {AgmCoreModule, GoogleMapsAPIWrapper} from '@agm/core';
import { environment } from '../environments/environment';
import { AngularFireModule } from 'angularfire2';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UsersComponent,
    MessagesComponent,
    UserSearchComponent,
    ClientsComponent,
    ClientComponent,
    BenchesComponent,
    BenchComponent,
    BenchFormComponent,
    AppointmentsComponent,
    QuestionnairesComponent,
    HealthworkersComponent,
    QuestionsComponent,
    QuestionFormComponent,
    QuestionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyC_piA7v5a0P4w94rxxm19v7zUlgX_7M5s',
      libraries: ["places", "geometry"]
    })

  ],
  providers: [
    UserService,
    MessageService,
    ClientsService,
    HandleErrorService,
    BenchesService,
    AppointmentsService,
    QuestionnairesService,
    HealthworkersService,
    CookieService,
    CookieService,
    QuestionsService,
    AuthenticationService,
    GoogleMapsAPIWrapper
],
  bootstrap: [AppComponent]
})
export class AppModule {
  userType = 'Admin';
}
