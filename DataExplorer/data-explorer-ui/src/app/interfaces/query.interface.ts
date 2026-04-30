export interface SavedQuery {
  id: string;
  categoryId: string;
  name: string;
  description: string;
}

export const queries: SavedQuery[] = [
  {
    id: 'b2c3d4e5-0002-0000-0000-000000000001',
    categoryId: 'a1b2c3d4-0001-0000-0000-000000000001',
    name: 'Get All Users',
    description: 'Fetches all user records.',
  },
  {
    id: 'b2c3d4e5-0002-0000-0000-000000000002',
    categoryId: 'a1b2c3d4-0001-0000-0000-000000000001',
    name: 'Find User by ID',
    description: 'Looks up a single user.',
  },
  {
    id: 'b2c3d4e5-0002-0000-0000-000000000003',
    categoryId: 'a1b2c3d4-0001-0000-0000-000000000002',
    name: 'List Products',
    description: 'Returns the product catalogue.',
  },
  {
    id: 'b2c3d4e5-0002-0000-0000-000000000004',
    categoryId: 'a1b2c3d4-0001-0000-0000-000000000002',
    name: 'Search Products',
    description: 'Full-text search on products.',
  },
  {
    id: 'b2c3d4e5-0002-0000-0000-000000000005',
    categoryId: 'a1b2c3d4-0001-0000-0000-000000000003',
    name: 'Recent Orders',
    description: 'Orders from the last 30 days.',
  },
];
