import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Post} from '../post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input()
  even: boolean;

  @Input()
  post: Post;

  tags: Array<string> = [];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.tags = this.post.tags;
  }
}

