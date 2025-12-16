import { Routes } from '@angular/router';
import {Login} from './users/login/login';
import {App} from './app';
import {OrgChart} from './orgs/org-chart/org-chart';
import {StructureForm} from './orgs/structure-form/structure-form';

export const routes: Routes = [
  {
    path: '',
    component: App
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'org-chart',
    component: OrgChart
  },
  {
    path: 'structure-form',
    component: StructureForm
  }
];
