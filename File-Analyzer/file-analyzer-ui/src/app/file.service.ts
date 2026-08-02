import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private baseUrl = 'http://localhost:8080/api/files';

  // Signals for state management
  files = signal<any[]>([]);
  loading = signal<boolean>(false);

  constructor(private http: HttpClient) {}

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req).pipe(
      tap(event => {
        // If upload finishes, refresh the list
        if (event.type === 4) { // HttpEventType.Response
          this.refreshFiles();
        }
      })
    );
  }

  getFiles(): Observable<any[]> {
    console.log('[DEBUG_LOG] Fetching files from API...');
    return this.http.get<any[]>(this.baseUrl).pipe(
      tap(data => {
        console.log('[DEBUG_LOG] Files received from API:', data);
        this.files.set(data || []);
      })
    );
  }

  refreshFiles(): void {
    console.log('[DEBUG_LOG] Refreshing files...');
    this.loading.set(true);
    // Add a small delay to ensure loading state is captured by UI if it's too fast
    this.getFiles().subscribe({
      next: () => {
        console.log('[DEBUG_LOG] Refresh success. Total files in signal:', this.files().length);
        setTimeout(() => {
          this.loading.set(false);
          console.log('[DEBUG_LOG] Loading set to false.');
        }, 100);
      },
      error: (err) => {
        console.error('[DEBUG_LOG] Refresh error:', err);
        this.loading.set(false);
      }
    });
  }

  getFileById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
}
