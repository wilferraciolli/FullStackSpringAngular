import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {UserListComponent} from './users/user-list/user-list.component';
import {AuthGuard} from './_helpers/auth.guard';
import {UserDetailsComponent} from './users/user-details/user-details.component';
import {UserProfileComponent} from './users/user-details/user-profile/user-profile.component';
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {TodoComponent} from './todos/todo/todo.component';
import {TodoListComponent} from './todos/todo-list/todo-list.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'todos', component: TodoListComponent },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  {
    path: 'userdetails/:id', component: UserDetailsComponent, canActivate: [AuthGuard], children: [
      { path: '', redirectTo: 'profile', pathMatch: 'full' },
      { path: 'profile', component: UserProfileComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
