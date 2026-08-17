import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./documents/documents-page/documents-page').then((m) => m.DocumentsPage),
  },
  {
    path: 'documents/:id',
    loadComponent: () =>
      import('./documents/document-details-page/document-details-page').then(
        (m) => m.DocumentDetailsPage,
      ),
  },
];
