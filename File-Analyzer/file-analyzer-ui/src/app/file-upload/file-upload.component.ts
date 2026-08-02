import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService } from '../file.service';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [
    CommonModule,
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
  currentFile?: File;
  progress = 0;
  message = '';
  fileName = '';
  extractedData: any = null;

  constructor(private uploadService: FileService) {}

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
    if (this.selectedFiles && this.selectedFiles.length > 0) {
      this.fileName = this.selectedFiles[0].name;
    }
  }

  upload(): void {
    this.progress = 0;
    this.message = '';
    this.extractedData = null;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.uploadService.upload(this.currentFile).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = 'File uploaded successfully!';
              this.extractedData = event.body;
            }
          },
          error: (err: any) => {
            console.error(err);
            this.progress = 0;
            this.message = 'Could not upload the file!';
            this.currentFile = undefined;
          }
        });
      }

      this.selectedFiles = undefined;
    }
  }
}
