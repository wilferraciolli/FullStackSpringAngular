import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  @Input()
  even: boolean;

  tags: Array<string> = [];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get(environment.baseUrl + '/api/tags')
      .subscribe((s: Array<Tag>) => {
          // console.log(s);
          this.tags = s.map((d: Tag) => d.description);
        }
      );

    // this.http.get(environment.baseUrl + '/api/tags')
    //   .pipe(
    //     map((d: Tag) => {
    //       console.log('The value of d is ', d);
    //       // console.log(d.description);
    //      // return d.description;
    //     //  d.description;
    //     })
    //   )
    //   .subscribe(s => {
    //     console.log(s);
    //    // this.tags.push(s);
    //     console.log('Value is ', this.tags);
    //   });
  }
}

export interface Tag {
  id: number;
  description: string;
}
