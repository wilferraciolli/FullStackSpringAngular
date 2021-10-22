import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Profile} from '../interfaces/profile';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  profile: Profile;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get(environment.baseUrl + 'profile')
      .subscribe((profile: Profile) => {
          // console.log(profile);

          this.profile = profile;
          localStorage.setItem('blog-profile', JSON.stringify(profile));
        }
      );
  }
}
