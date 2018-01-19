import { NgModule } from '@angular/core';
import { UserComponent } from './user/user.component';
import { RouterModule, Routes } from '@angular/router';
import {UsersComponent} from './users/users.component';

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'users', component: UsersComponent }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
