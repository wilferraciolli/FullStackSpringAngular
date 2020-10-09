import {Component, OnInit} from '@angular/core';
import {UserProfile} from './user.profile';
import {UserProfileService} from '../_services/user.profile.service';
import {AuthenticationService} from '../_services/authentication.service';
import {Todo} from '../todos/todo';
import {LinksService} from '../_services/links-service';
import {Link} from '../shared/response/link';
import {TodoResponse} from '../todos/todo-response';
import {TodoService} from '../todos/todo.service';
import {TodoMeta} from '../todos/todo-meta';
import {TodoLinksCollection} from '../todos/todo-links-collection';
import {TodoAdapter} from '../todos/todo.adapter';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userProfile: UserProfile;

  completedTodoLink: Link;
  activeTodoLink: Link;
  upcomingTodoLink: Link;

  constructor(private userProfileService: UserProfileService,
              private authenticationService: AuthenticationService,
              private linksService: LinksService,
              private todoService: TodoService,
              private adapter: TodoAdapter) {
  }

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedOn) {
      //  this.loadUserProfile();
    } else {
      console.log('user not authenticated');
    }
    this.userProfileService.loadUserProfile()
      .then((data) => {
        this.userProfile = new UserProfile(data);
        this.completedTodoLink = this.userProfile.links.completedTodos;
        this.activeTodoLink = this.userProfile.links.activeTodos;
        this.upcomingTodoLink = this.userProfile.links.newTodos;
      });
  }

  convertResponse(collectionBody: any[]): Array<Todo> {

    return collectionBody.map(item =>
      this.adapter.adapt(item._data, item._links, item._meta));
  }

}
