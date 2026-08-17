import { DatePipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';

import { DocumentsService } from '../documents.service';

@Component({
  selector: 'app-documents-page',
  imports: [
    DatePipe,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatTableModule,
    MatToolbarModule,
  ],
  templateUrl: './documents-page.html',
  styleUrl: './documents-page.scss',
})
export class DocumentsPage {
  private readonly documentsService = inject(DocumentsService);
  private readonly snackBar = inject(MatSnackBar);

  protected readonly documents = this.documentsService.documents;
  protected readonly uploading = signal(false);
  protected readonly displayedColumns = ['name', 'type', 'size', 'createdAt'];

  protected onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    input.value = '';
    if (!file) {
      return;
    }

    this.uploading.set(true);
    this.documentsService.upload(file).subscribe({
      next: () => {
        this.uploading.set(false);
        this.documents.reload();
      },
      error: (error) => {
        this.uploading.set(false);
        this.snackBar.open(`Upload failed: ${error.error?.message ?? error.message}`, 'Dismiss', {
          duration: 5000,
        });
      },
    });
  }

  protected formatSize(bytes: number): string {
    if (bytes < 1024) {
      return `${bytes} B`;
    }
    const units = ['KB', 'MB', 'GB'];
    let value = bytes / 1024;
    let unitIndex = 0;
    while (value >= 1024 && unitIndex < units.length - 1) {
      value /= 1024;
      unitIndex++;
    }
    return `${value.toFixed(1)} ${units[unitIndex]}`;
  }
}
