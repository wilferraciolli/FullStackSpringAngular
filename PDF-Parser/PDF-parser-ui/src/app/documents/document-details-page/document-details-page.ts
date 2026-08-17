import { DatePipe } from '@angular/common';
import { Component, effect, inject, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';

import { DocumentsService } from '../documents.service';
import { formatSize } from '../format-size';
import { isParsingPending, statusBadgeClass, statusLabel } from '../parsing-status';

const POLL_INTERVAL_MS = 2000;

@Component({
  selector: 'app-document-details-page',
  imports: [
    DatePipe,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatProgressBarModule,
    MatToolbarModule,
    RouterLink,
  ],
  templateUrl: './document-details-page.html',
  styleUrl: './document-details-page.scss',
})
export class DocumentDetailsPage {
  private readonly documentsService = inject(DocumentsService);

  readonly id = input.required<string>();

  protected readonly documentResource = this.documentsService.getById(() => this.id());
  protected readonly formatSize = formatSize;
  protected readonly statusLabel = statusLabel;
  protected readonly statusBadgeClass = statusBadgeClass;
  protected readonly isParsingPending = isParsingPending;

  constructor() {
    effect((onCleanup) => {
      const document = this.documentResource.value();
      if (document && isParsingPending(document.statusCode)) {
        const timeoutId = setTimeout(() => this.documentResource.reload(), POLL_INTERVAL_MS);
        onCleanup(() => clearTimeout(timeoutId));
      }
    });
  }
}
