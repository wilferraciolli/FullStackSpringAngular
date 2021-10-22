import { Component, OnInit } from '@angular/core';
import {Post} from '../post/post';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  articles: Array<Post>;

  constructor() {
    // this.articles = [new Post()];
    // console.log(this.articles.length);
  }

  ngOnInit(): void {
    this.articles = [new Post(), new Post(), new Post(), new Post(), new Post(), new Post()];
  }

  isEven(index: number): boolean {
    return index % 2 === 0;
  }
}
