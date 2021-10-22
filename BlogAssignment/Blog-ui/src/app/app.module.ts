import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { SideNavNavigationComponent } from './side-nav-navigation/side-nav-navigation.component';
import {HttpClientModule} from '@angular/common/http';
import { PostComponent } from './posts/post/post.component';
import { PostListComponent } from './posts/post-list/post-list.component';
import { PostActionComponent } from './posts/post-action/post-action.component';
import { PostTagsComponent } from './posts/post-tags/post-tags.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    FooterComponent,
    SideNavComponent,
    HomeComponent,
    AboutComponent,
    PortfolioComponent,
    SideNavNavigationComponent,
    PostComponent,
    PostListComponent,
    PostActionComponent,
    PostTagsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
