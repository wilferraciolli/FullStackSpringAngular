export type DocumentParsingStatus = 'FILE_UPLOADED' | 'PROCESSING' | 'OK' | 'FAILED' | 'INSUFFICIENT_DATA';

interface StatusPresentation {
  readonly label: string;
  readonly cssClass: string;
}

const STATUS_PRESENTATION: Record<DocumentParsingStatus, StatusPresentation> = {
  FILE_UPLOADED: { label: 'Uploaded', cssClass: 'status-badge status-badge--file-uploaded' },
  PROCESSING: { label: 'Processing', cssClass: 'status-badge status-badge--processing' },
  OK: { label: 'Parsed', cssClass: 'status-badge status-badge--ok' },
  FAILED: { label: 'Failed', cssClass: 'status-badge status-badge--failed' },
  INSUFFICIENT_DATA: { label: 'Insufficient data', cssClass: 'status-badge status-badge--insufficient-data' },
};

export function statusLabel(status: DocumentParsingStatus): string {
  return STATUS_PRESENTATION[status].label;
}

export function statusBadgeClass(status: DocumentParsingStatus): string {
  return STATUS_PRESENTATION[status].cssClass;
}

export function isParsingPending(status: DocumentParsingStatus): boolean {
  return status === 'FILE_UPLOADED' || status === 'PROCESSING';
}
