import { HttpClient, httpResource } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Document } from './document.model';

@Injectable({ providedIn: 'root' })
export class DocumentsService {
  private readonly http = inject(HttpClient);

  readonly documents = httpResource<Document[]>(() => '/api/documents', { defaultValue: [] });

  getById(id: () => string) {
    return httpResource<Document>(() => `/api/documents/${id()}`);
  }

  upload(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<Document>('/api/documents', formData);
  }

  delete(id: string) {
    return this.http.delete<void>(`/api/documents/${id}`);
  }
}
