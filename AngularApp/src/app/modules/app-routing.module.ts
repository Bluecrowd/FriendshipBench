import { NgModule } from '@angular/core';
import { UserComponent } from '../components/user/user.component';
import { RouterModule, Routes } from '@angular/router';
import {UsersComponent} from '../components/users/users.component';
import {ClientsComponent} from '../components/clients/clients.component';
import {BenchesComponent} from '../components/benches/benches.component';
import {AppointmentsComponent} from '../components/appointments/appointments.component';
import {QuestionnairesComponent} from '../components/questionnaires/questionnaires.component';
import {HealthworkersComponent} from '../components/healthworkers/healthworkers.component';
import {QuestionsComponent} from '../components/questions/questions.component';
import {ClientComponent} from '../components/client/client.component';

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'users', component: UsersComponent },
  { path: 'clients', component: ClientsComponent },
  { path: 'clients/:id', component: ClientComponent },
  { path: 'benches', component: BenchesComponent },
  { path: 'appointments', component: AppointmentsComponent },
  { path: 'questionnaires', component: QuestionnairesComponent },
  { path: 'healthworkers', component: HealthworkersComponent },
  { path: 'questions', component: QuestionsComponent },
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
