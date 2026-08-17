# PDF Parser

A full-stack application for uploading PDF documents (including scanned/image-based ones) and
extracting their text. Lab results are the primary use case — some come as clean digital PDFs,
others as scans, so the pipeline needs both native text extraction and OCR.

## Architecture

| Component        | Tech                                | Responsibility                                                        |
|-------------------|--------------------------------------|-------------------------------------------------------------------------|
| `PDF-parser-ui`   | Angular 22 + Material                | Upload documents, browse the list, view a document's details/parsed text |
| `pdf-parser-api`  | Spring Boot (Java 25)                | REST API — document metadata + file storage, orchestrates parsing       |
| `pdf-ocr-service` | Python (FastAPI) + PyMuPDF + Tesseract | Stateless PDF→text extraction, with OCR fallback for scanned pages    |

```
┌────────────┐   upload PDF   ┌──────────────────┐   PDF bytes   ┌───────────────────┐
│ Angular UI │ ─────────────▶ │  Spring Boot API  │ ────────────▶ │ Python OCR Service │
└────────────┘ ◀───────────── └──────────────────┘ ◀──────────── └───────────────────┘
                 doc + status            │  persists doc,          extracted text
                                         │  content, and
                                         ▼  parse status/result
                                  ┌────────────┐
                                  │  H2 (file) │
                                  └────────────┘
```

`pdf-parser-api` owns all state (documents, their bytes, and parsing status/results).
`pdf-ocr-service` is a stateless transformation service — it has no database and doesn't know
what a "document" is; it just takes PDF bytes and returns extracted text.

## Projects

### [`pdf-parser-api`](pdf-parser-api/README.md)
Spring Boot service exposing REST endpoints to upload PDFs, list/fetch/delete them, and track
parsing status and parsed text. Spring Data JPA + Flyway for persistence, Bean Validation for
request validation, H2 (file-based) as the database. Calls `pdf-ocr-service` asynchronously after
upload and stores the result.

### [`PDF-parser-ui`](PDF-parser-ui/README.md)
Angular application: a documents list with upload/delete, and a document details page. Built with
signals throughout (`httpResource`, signal inputs, zoneless change detection) and Angular
Material.

### [`pdf-ocr-service`](pdf-ocr-service/README.md)
Python/FastAPI service with a single `POST /parse` endpoint. Extracts each page's native text
layer where there is one (PyMuPDF), and falls back to Tesseract OCR for pages that are just
images (e.g. scanned lab reports). Runs in Docker since Tesseract is a native binary, not a Python
package.

## Status

Working end-to-end: upload a PDF in the UI → the API stores it and kicks off parsing
asynchronously (status `FILE_UPLOADED` → `PROCESSING`) → the OCR service extracts text (native
text layer, or Tesseract OCR for scanned pages) → the API stores the result (`OK` /
`INSUFFICIENT_DATA` / `FAILED`) → the UI polls and shows the live status and, once done, the
parsed text on the document details page.

Not yet done: retrying a failed/insufficient parse, downloading a document's raw bytes, and any
structured extraction beyond raw text (e.g. pulling out individual lab values).

## Running everything with Docker Compose

This is the easiest way to run the full stack — it also avoids installing Python/Tesseract
natively, since `pdf-ocr-service` needs both.

**Prerequisite:** [Docker Desktop](https://www.docker.com/products/docker-desktop/) running.

```bash
docker compose up --build
```

This builds and starts all three services:

| Service           | URL                     |
|--------------------|--------------------------|
| `pdf-parser-ui`    | http://localhost:4200    |
| `pdf-parser-api`   | http://localhost:8080    |
| `pdf-ocr-service`  | http://localhost:8000    |

Open `http://localhost:4200` — the UI's nginx container proxies `/api/*` to `pdf-parser-api`, so
no extra configuration is needed.

**Every time you change code, rebuild with `--build`** — Compose caches image layers, so
unaffected services rebuild quickly, but a plain `docker compose up` without `--build` will just
reuse the old images and won't pick up your changes:

```bash
docker compose up --build
```

To stop everything:

```bash
docker compose down
```

The API's H2 database lives in a named Docker volume (`api-data`) so it survives rebuilds/restarts.
To wipe it and start fresh:

```bash
docker compose down -v
```

To build/run a single service (useful when only working on one piece):

```bash
docker compose up --build pdf-ocr-service
```

## Running natively (without Docker)

Each project's own README has the details; in short:

```bash
# Terminal 1 — API (needs Java 25 + Maven, use the wrapper)
cd pdf-parser-api
./mvnw spring-boot:run

# Terminal 2 — UI (needs Node)
cd PDF-parser-ui
npm install
npm start

# Terminal 3 — OCR service (needs Python 3.12 + Tesseract installed natively — see its README)
cd pdf-ocr-service
python -m venv .venv && .venv\Scripts\activate
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

The OCR service is the one with real native-dependency friction (Tesseract), so Docker Compose is
the recommended way to run it even if you run the API/UI natively.
