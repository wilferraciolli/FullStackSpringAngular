import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LinksService } from '../../_services/links-service';
import * as _ from 'lodash';
import { UserProfile } from '../../home/user.profile';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  id: string;
  userProfile: UserProfile;
  personSelfLink: string;
  personCarsLink: string;

  activeLinkIndex = -1;
  routeLinks: any[];

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private linksService: LinksService) {

    // create navigation links for children
    this.routeLinks = [
      {
        label: 'Profile',
        link: './profile',
        index: 0
      }, {
        label: 'Cars',
        link: './cars',
        index: 1
      }
    ];
  }

  ngOnInit(): void {
    // send the request to home if no user details is present
    if (_.isUndefined(history.state.data)) {
      this.router.navigate(['/home']);
    } else {

      // get the active children to display its component
      this.router.events.subscribe((res) => {
        this.activeLinkIndex = this.routeLinks.indexOf(this.routeLinks.find(tab => tab.link === '.' + this.router.url));
      });

      // get the data sent from the previous component
      this.id = this.activatedRoute.snapshot.params.id;
      this.userProfile = history.state.data.userProfile;
      this.personSelfLink = this.userProfile.links.person.href;
      this.personCarsLink = this.userProfile.links.users.href;
    }
  }

}
