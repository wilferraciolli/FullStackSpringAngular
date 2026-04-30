import { Routes } from '@angular/router';
import { Home } from './components/common/home/home';
import { QueryManager } from './components/query-manager/query-manager';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'home', component: Home },
  { path: 'query-manager', component: QueryManager },
  { path: '**', redirectTo: '' },
];
