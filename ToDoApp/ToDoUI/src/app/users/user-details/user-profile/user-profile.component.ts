import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserProfile } from '../../../home/user.profile';
import { UserDetailsComponent } from '../user-details.component';
import * as _ from 'lodash';
import { UserDetailsProfileService } from './user-details-profile.service';
import { PersonAdapter } from '../../../people/person.adapter';
import { Person } from '../../../people/person';
import { ValueViewValue } from '../../../shared/response/value-viewValue';
import { PersonPayload } from '../../../people/person-payload';
import { NotificationService } from '../../../shared/notification.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  id: string;
  personSelfLink: string;
  userProfile: UserProfile;
  person: Person;

  genders: Array<ValueViewValue>;
  maritalStatuses: Array<ValueViewValue>;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private notificationService: NotificationService,
              private userDetailsComponent: UserDetailsComponent,
              public userDetailsProfileService: UserDetailsProfileService,
              private adapter: PersonAdapter) {
  }

  ngOnInit() {
    this.getDataFromParentComponent();

    this.userDetailsProfileService.getById(this.personSelfLink)
      .then((data) => {
        this.userDetailsProfileService.populateForm(this.adapter.adapt(data._data, data._links, data._meta));
        this.person = this.adapter.adapt(data._data, data._links, data._meta);

        this.genders = this.getGenders(Object.values(this.person.meta).filter(g => g.hasOwnProperty('genderId')));
        this.maritalStatuses = this.getMaritalStatuses(Object.values(this.person.meta).filter(g => g.hasOwnProperty('maritalStatusId')));
      });

  }

  updatePerson() {
    console.log('Updating person');

    const updateUrl = this.userDetailsProfileService.form.value.links.updatePerson.href;

    this.userDetailsProfileService.update(updateUrl, new PersonPayload(this.userDetailsProfileService.getFormValue()))
      .subscribe(data => {
          console.log('success ', data);
          this.notificationService.success('Person updated successfully');
        },
        error => {
          console.log('Error', error);
          this.notificationService.error('Person could not be updated');
        }
      );
  }

  getDataFromParentComponent() {
    if (_.isUndefined(this.userDetailsComponent.userProfile)) {
      this.router.navigate(['/home']);
    } else {
      // get the user details from the parent
      this.id = this.activatedRoute.parent.snapshot.params.id;
      this.userProfile = this.userDetailsComponent.userProfile;
      this.personSelfLink = this.userProfile.links.person.href;
    }
  }

  getGenders(values: any):
    Array<ValueViewValue> {

    return values
      .map(v => v.genderId.values
        .map(meta => ({ value: meta.id, viewValue: meta.value })))[0];
  }

  getMaritalStatuses(values: any):
    Array<ValueViewValue> {
    // console.log('value extracted is ', values);

    return values
      .map(v => v.maritalStatusId.values
        .map(meta => ({ value: meta.id, viewValue: meta.value })))[0];
  }
}
