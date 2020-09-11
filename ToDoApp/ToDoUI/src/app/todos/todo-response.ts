import { Todo } from './todo';

export interface TodoResponse {
  _embedded: {
    collection: [
      {
        _links: { self: { href: string } },
        _data: { todo: Todo }
      }
    ];
    _links: {
      self: {
        href: string
      },
      createTodo: {
        href: string
      },
      updateTodo: {
        href: string
      },
      deleteTodo: {
        href: string
      },
      todos: {
        href: string
      }
    };
  };
  _links: {
    self: {
      href: string
    },
    createTodo: {
      href: string
    }
  };
  _meta: { values: { id: string } };
}
