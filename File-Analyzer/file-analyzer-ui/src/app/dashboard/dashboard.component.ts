import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, MatCardModule, MatButtonModule, MatIconModule],
  template: `
    <div class="dashboard-container">
      <h1>File Analyzer Dashboard</h1>
      <div class="cards-grid">
        <mat-card class="dashboard-card">
          <mat-card-header>
            <mat-icon mat-card-avatar>description</mat-icon>
            <mat-card-title>View Documents</mat-card-title>
            <mat-card-subtitle>See all uploaded and processed files</mat-card-subtitle>
          </mat-card-header>
          <mat-card-actions>
            <a mat-button color="primary" [routerLink]="['/files']">GO TO DOCUMENTS</a>
          </mat-card-actions>
        </mat-card>

        <mat-card class="dashboard-card">
          <mat-card-header>
            <mat-icon mat-card-avatar>cloud_upload</mat-icon>
            <mat-card-title>Upload Document</mat-card-title>
            <mat-card-subtitle>Upload a new blood test PDF for analysis</mat-card-subtitle>
          </mat-card-header>
          <mat-card-actions>
            <a mat-button color="accent" [routerLink]="['/upload']">GO TO UPLOAD</a>
          </mat-card-actions>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 24px;
      max-width: 1200px;
      margin: 0 auto;
    }
    .cards-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 24px;
      margin-top: 24px;
    }
    .dashboard-card {
      padding: 16px;
    }
    h1 {
      color: #3f51b5;
      margin-bottom: 8px;
    }
  `]
})
export class DashboardComponent {}
