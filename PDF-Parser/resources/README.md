# Test PDFs

Sample files for manually testing upload + parsing through the UI.

- **`lab-result-text.pdf`** — a native-text PDF (mock lab report). Exercises the PyMuPDF
  text-layer extraction path — parses instantly, `usedOcr: false`.
- **`lab-result-scanned.pdf`** — the same mock report, but rendered as an image with no text
  layer (simulating a scanned document). Exercises the Tesseract OCR fallback path —
  `usedOcr: true`. OCR isn't perfect, so expect a few misread characters in the result.

Upload either one through the UI at `http://localhost:4200` (or `POST /api/documents` directly)
to see the parsing status flow from `FILE_UPLOADED` → `PROCESSING` → `OK`.
