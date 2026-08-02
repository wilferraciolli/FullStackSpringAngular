import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FileListComponent } from './file-list/file-list.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'upload', component: FileUploadComponent },
  { path: 'files', component: FileListComponent },
  { path: '**', redirectTo: '/dashboard' }
];
