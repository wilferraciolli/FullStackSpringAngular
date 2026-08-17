# PDF OCR Service

A small, stateless Python service that extracts text from PDF bytes. It's called by the
[PDF Parser API](../pdf-parser-api/README.md) — it doesn't know anything about documents, ids, or
statuses; it just takes bytes and returns text.

## What it does

For each page in the PDF:
1. Try to read the native text layer (`PyMuPDF`).
2. If a page has no extractable text (e.g. it's a scanned image), rasterize that page and run it
   through Tesseract OCR (`pytesseract`) instead.

This means a single PDF can mix native-text pages and scanned pages, and each page is handled the
right way automatically.

## Tech stack

- Python 3.12
- FastAPI + Uvicorn
- PyMuPDF (`fitz`) — PDF parsing and page rasterization (no Poppler dependency needed)
- Tesseract OCR (via `pytesseract`) — OCR fallback for image-based pages

## API

### `POST /parse`

`multipart/form-data` with a `file` field containing the PDF.

Response:

```json
{
  "text": "...extracted text...",
  "pageCount": 3,
  "usedOcr": true
}
```

`usedOcr` is `true` if at least one page needed the OCR fallback. Returns `400` for an empty file
body, `422` if the bytes can't be parsed as a PDF.

### `GET /health`

Liveness check — returns `{"status": "ok"}`.

## Running it

This service depends on the Tesseract OCR binary, which isn't a Python package — Docker is the
easiest way to run it without installing Tesseract natively. From the repo root:

```bash
docker compose up --build pdf-ocr-service
```

The service listens on `http://localhost:8000`.

### Running natively (without Docker)

If you'd rather not use Docker, install Python 3.12+, [Tesseract
OCR](https://github.com/UB-Mannheim/tesseract/wiki) (Windows) or `apt-get install tesseract-ocr`
(Linux), then:

```bash
cd pdf-ocr-service
python -m venv .venv
.venv\Scripts\activate      # Windows; use `source .venv/bin/activate` on Linux/macOS
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

## Status

Standalone and working on its own (`/parse` and `/health`). Not yet wired up to the Spring Boot
API — that integration (async call on upload, status tracking, storing the result) is the next
step.
