import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileService } from '../file.service';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
  selector: 'app-file-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatButtonModule, MatIconModule, MatExpansionModule],
  template: `
    <div class="list-container">
      <div class="header">
        <h1>Analyzed Documents</h1>
        <button mat-flat-button color="primary" routerLink="/upload">
          <mat-icon>add</mat-icon> UPLOAD NEW
        </button>
      </div>

      <div *ngIf="files.length === 0" class="empty-state">
        <mat-icon>history</mat-icon>
        <p>No documents found. Upload your first blood test to see results here.</p>
      </div>

      <mat-accordion multi="true">
        <mat-expansion-panel *ngFor="let file of files" class="file-panel">
          <mat-expansion-panel-header>
            <mat-panel-title>
              <mat-icon>description</mat-icon>
              {{ file.fileName }}
            </mat-panel-title>
            <mat-panel-description>
              Processed on {{ file.processedAt | date:'medium' }}
            </mat-panel-description>
          </mat-expansion-panel-header>

          <div class="panel-content">
            <div class="data-section">
              <h3>Extracted Data (JSON)</h3>
              <pre>{{ file.extractedData | json }}</pre>
            </div>

            <div class="data-section">
              <h3>Raw Text</h3>
              <div class="raw-text-box">
                {{ file.rawText }}
              </div>
            </div>

            <div class="metadata">
              <span><strong>ID:</strong> {{ file.id }}</span>
              <span><strong>Size:</strong> {{ (file.size / 1024) | number:'1.0-2' }} KB</span>
              <span><strong>Type:</strong> {{ file.contentType }}</span>
            </div>
          </div>
        </mat-expansion-panel>
      </mat-accordion>
    </div>
  `,
  styles: [`
    .list-container {
      padding: 24px;
      max-width: 1000px;
      margin: 0 auto;
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 24px;
    }
    .empty-state {
      text-align: center;
      padding: 48px;
      color: #777;
    }
    .empty-state mat-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
    }
    .file-panel {
      margin-bottom: 12px;
    }
    .panel-content {
      display: flex;
      flex-direction: column;
      gap: 20px;
      padding: 16px 0;
    }
    .data-section h3 {
      margin-top: 0;
      border-bottom: 1px solid #eee;
      padding-bottom: 8px;
      color: #3f51b5;
    }
    pre {
      background: #f5f5f5;
      padding: 12px;
      border-radius: 4px;
      overflow-x: auto;
      max-height: 200px;
    }
    .raw-text-box {
      white-space: pre-wrap;
      font-family: monospace;
      font-size: 12px;
      background: #fafafa;
      padding: 12px;
      border-radius: 4px;
      border: 1px solid #eee;
      max-height: 200px;
      overflow-y: auto;
    }
    .metadata {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      font-size: 12px;
      color: #666;
      border-top: 1px solid #eee;
      padding-top: 12px;
    }
    mat-panel-title mat-icon {
      margin-right: 8px;
    }
  `]
})
export class FileListComponent implements OnInit {
  files: any[] = [];

  constructor(private fileService: FileService) {}

  ngOnInit(): void {
    this.loadFiles();
  }

  loadFiles(): void {
    this.fileService.getFiles().subscribe({
      next: (data) => {
        this.files = data;
      },
      error: (err) => console.error('Error fetching files', err)
    });
  }
}
