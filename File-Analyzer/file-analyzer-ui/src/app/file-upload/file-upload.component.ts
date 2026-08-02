import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService } from '../file.service';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatProgressBarModule,
    MatCardModule,
    MatIconModule,
    MatListModule
  ],
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent {
  selectedFiles?: FileList;
  currentFile = signal<File | undefined>(undefined);
  progress = signal<number>(0);
  message = signal<string>('');
  fileName = signal<string>('');
  extractedData = signal<any>(null);

  constructor(private uploadService: FileService) {}

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
    if (this.selectedFiles && this.selectedFiles.length > 0) {
      this.fileName.set(this.selectedFiles[0].name);
    }
  }

  upload(): void {
    this.progress.set(0);
    this.message.set('');
    this.extractedData.set(null);

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile.set(file);

        this.uploadService.upload(file).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress.set(Math.round(100 * event.loaded / (event.total || event.loaded)));
            } else if (event instanceof HttpResponse) {
              this.message.set('File uploaded successfully!');
              this.extractedData.set(event.body);
              this.currentFile.set(undefined);
              this.selectedFiles = undefined;
            }
          },
          error: (err: any) => {
            console.error(err);
            this.progress.set(0);
            this.message.set('Could not upload the file!');
            this.currentFile.set(undefined);
          }
        });
      }
    }
  }
}
