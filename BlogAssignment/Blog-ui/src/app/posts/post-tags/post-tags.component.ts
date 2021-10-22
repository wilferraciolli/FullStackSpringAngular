import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-post-tags',
  templateUrl: './post-tags.component.html',
  styleUrls: ['./post-tags.component.scss']
})
export class PostTagsComponent implements OnInit {

  @Input()
  tags: Array<string> = [];
  // tags: Array<string> = ['#tag1', '#tag2', '#tag3'];

  constructor() {
  }

  ngOnInit(): void {
  }

}
