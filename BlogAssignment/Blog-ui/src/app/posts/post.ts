import {Link} from '../interfaces/link';

export interface Post {

  id: number;
  title: string;
  content: string;
  creationDateTime: string;
  tags: Array<string>;
  likesCount: number;
  links: Array<Link>;

}
