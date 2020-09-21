import {TodoLinks} from './todo-links';
import {TodoMeta} from './todo-meta';

export class Todo {

  id: string;
  typeId: string;
  name: string;
  description: string;
  createdDateTime: string;
  completedDateTime?: string;
  stateId: string;
  completionStats: number;
  published: string;
  links: TodoLinks;
  meta: Array<TodoMeta>;


  constructor(id: string, typeId: string, name: string, description: string, createdDateTime: string,
              completedDateTime: string, stateId: string, completionStats: number, published: string,
              links: TodoLinks, meta: Array<TodoMeta>) {
    this.id = id;
    this.typeId = typeId;
    this.name = name;
    this.description = description;
    this.createdDateTime = createdDateTime;
    this.completedDateTime = completedDateTime;
    this.stateId = stateId;
    this.completionStats = completionStats;
    this.published = published;
    this.links = links;
    this.meta = meta;
  }
}
