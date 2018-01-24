import { NgModule } from '@angular/core';
import { UserComponent } from '../components/user/user.component';
import { RouterModule, Routes } from '@angular/router';
import {UsersComponent} from '../components/users/users.component';
import {ClientsComponent} from '../components/clients/clients.component';
import {BenchesComponent} from '../components/benches/benches.component';
import {GoogleMapsComponent} from "../components/google-maps/google-maps.component";

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'users', component: UsersComponent },
  { path: 'clients', component: ClientsComponent },
  { path: 'benches', component: BenchesComponent },
  { path: 'googleMaps', component: GoogleMapsComponent },
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
