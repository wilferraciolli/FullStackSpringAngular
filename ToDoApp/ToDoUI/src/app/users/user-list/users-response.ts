import { User } from '../user';
import { MetaDataRules, MetaDataWithValues } from '../../shared/response/meta-data';

export interface UsersResponse {
  _embedded: {
    collection: [
      {
        _links: { self: { href: string } },
        _data: { user: User }
      }
    ];
    _links: {
      self: {
        href: string
      },
      updateUser: {
        href: string
      },
      deleteUser: {
        href: string
      },
      users: {
        href: string
      }
    };
  };
  _links: {
    self: {
      href: string
    },
    createUser: {
      href: string
    }
  };
  _meta: [
    {
      id: MetaDataRules,
      roleIds: MetaDataWithValues
    }
  ];
}
