import { SavedQuery } from './query.interface';

export interface QueryCategory {
  id: string;
  name: string;
  type: string;
}

export const categories: QueryCategory[] = [
  { id: 'a1b2c3d4-0001-0000-0000-000000000001', name: 'User Queries', type: 'custom' },
  { id: 'a1b2c3d4-0001-0000-0000-000000000002', name: 'Product Queries', type: 'custom' },
  { id: 'a1b2c3d4-0001-0000-0000-000000000003', name: 'Order Queries', type: 'custom' },
];
