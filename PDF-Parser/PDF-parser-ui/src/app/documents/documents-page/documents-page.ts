import { DatePipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';

import { Document } from '../document.model';
import { DocumentsService } from '../documents.service';
import { formatSize } from '../format-size';

@Component({
  selector: 'app-documents-page',
  imports: [
    DatePipe,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatTableModule,
    MatToolbarModule,
    RouterLink,
  ],
  templateUrl: './documents-page.html',
  styleUrl: './documents-page.scss',
})
export class DocumentsPage {
  private readonly documentsService = inject(DocumentsService);
  private readonly snackBar = inject(MatSnackBar);

  protected readonly documents = this.documentsService.documents;
  protected readonly uploading = signal(false);
  protected readonly displayedColumns = ['name', 'type', 'size', 'createdAt', 'actions'];
  protected readonly formatSize = formatSize;

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

  protected onDelete(document: Document): void {
    if (!confirm(`Delete "${document.name}"? This cannot be undone.`)) {
      return;
    }

    this.documentsService.delete(document.id).subscribe({
      next: () => this.documents.reload(),
      error: (error) => {
        this.snackBar.open(`Delete failed: ${error.error?.message ?? error.message}`, 'Dismiss', {
          duration: 5000,
        });
      },
    });
  }
}
