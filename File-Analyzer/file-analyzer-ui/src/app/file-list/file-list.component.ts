import { Component, OnInit, inject, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FileService } from '../file.service';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@Component({
  selector: 'app-file-list',
  standalone: true,
  imports: [CommonModule, RouterModule, MatTableModule, MatCardModule, MatButtonModule, MatIconModule, MatExpansionModule, MatProgressBarModule],
  templateUrl: './file-list.component.html',
  styleUrl: './file-list.component.scss'
})
export class FileListComponent implements OnInit {
  private fileService: FileService = inject(FileService);

  files: Signal<any[]> = this.fileService.files;
  loading: Signal<boolean> = this.fileService.loading;

  ngOnInit(): void {
    this.fileService.refreshFiles();
  }
}
