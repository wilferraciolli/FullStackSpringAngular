import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {ProjectMaterialModule} from './app.module.material';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers/error.interceptor';
import {MAT_DATE_LOCALE} from '@angular/material/core';
import {environment} from '../environments/environment';
import {LoginComponent} from './login/login.component';
import {PersonComponent} from './people/person/person.component';
import {RegistrationComponent} from './registration/registration.component';
import {UserProfileComponent} from './users/user-details/user-profile/user-profile.component';
import {UserDetailsComponent} from './users/user-details/user-details.component';
import {UserListComponent} from './users/user-list/user-list.component';
import {ConvertIdsToStringValuePipe} from './users/user-role-mapper-pipe';
import {UserComponent} from './users/user/user.component';
import {MatConfirmDialogComponent} from './shared/components/mat-confirm-dialog/mat-confirm-dialog.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TodoListComponent} from './todos/todo-list/todo-list.component';
import {TodoComponent} from './todos/todo/todo.component';
import {BooleanPipePipe} from './todos/boolean-pipe.pipe';
import {MatSliderModule} from '@angular/material/slider';
import {TodoCardComponent} from './shared/components/todo-card/todo-card.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    MatConfirmDialogComponent,
    PersonComponent,
    RegistrationComponent,
    UserProfileComponent,
    UserDetailsComponent,
    UserListComponent,
    ConvertIdsToStringValuePipe,
    UserComponent,
    PageNotFoundComponent,
    TodoListComponent,
    TodoComponent,
    BooleanPipePipe,
    TodoCardComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        ProjectMaterialModule,
        ReactiveFormsModule,
        MatSliderModule
    ],
  providers: [
    {provide: MatDialogRef, useValue: {}},
    {provide: MAT_DIALOG_DATA, useValue: []},
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    {provide: MAT_DATE_LOCALE, useValue: environment.locale}
  ],
  bootstrap: [AppComponent],
// add components used in pop ups
  entryComponents: [ UserComponent, TodoComponent, MatConfirmDialogComponent]
})
export class AppModule {
}
