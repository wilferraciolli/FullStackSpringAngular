export interface User {
  id: string;
  username: string;
  role: 'ADMIN' | 'USER';
  managerId?: string; // For the Org Chart logic
  token?: string | undefined;
}
