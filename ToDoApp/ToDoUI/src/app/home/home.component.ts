import { Component, OnInit } from '@angular/core';
import {UserProfile} from "./user.profile";
import {UserProfileService} from "../_services/user.profile.service";
import {AuthenticationService} from "../_services/authentication.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userProfile: UserProfile;

  constructor(private userProfileService: UserProfileService,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedOn) {
      //  this.loadUserProfile();
    } else {
      console.log('user not authenticated');
    }
  }

}
