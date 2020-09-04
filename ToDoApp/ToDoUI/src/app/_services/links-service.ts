import { Injectable } from '@angular/core';
import { Link } from '../shared/response/link';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class LinksService {

  constructor() {
  }

  /**
   * Determines if a ling is present.
   * @param link true if the link is defined.
   */
  public hasLink(link: Link): boolean {
    // console.log(link);

    return !_.isUndefined(link);
  }
}
