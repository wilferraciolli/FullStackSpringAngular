import { Injectable } from '@angular/core';
import { HttpBaseService } from '../shared/response/base-service';

@Injectable({
  providedIn: 'root'
})
export class TodoService extends HttpBaseService {

  todoTemplateUrl: string;
  todosUrl: string;

}
