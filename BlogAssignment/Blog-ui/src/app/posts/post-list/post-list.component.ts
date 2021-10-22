import {Component, OnInit} from '@angular/core';
import {Post} from '../post';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Profile} from '../../interfaces/profile';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  profile: Profile;
  posts: Array<Post>;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.profile = JSON.parse(localStorage.getItem('blog-profile'));

    if (this.profile && this.profile.links && this.profile.links.find(link => link.name === 'posts')) {
      console.log('doing a get on posts ');
      this.http.get(environment.baseUrl + this.profile.links.find(link => link.name === 'posts').href)
        .subscribe((posts: Array<Post>) => {
            console.log('posts response ', posts);

            this.posts = posts;
            // this.posts = [new Post(), new Post(), new Post(), new Post(), new Post(), new Post()];
          }
        );
    }
  }

  isEven(index: number): boolean {
    return index % 2 === 0;
  }
}
