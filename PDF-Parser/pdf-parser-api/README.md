# PDF Parser API

Spring Boot REST API for uploading PDF documents and managing their metadata, content, and parsed
text. It's the backend for the [PDF Parser](../README.md) project — the Angular UI talks to this
API to upload files and browse previously uploaded documents. On upload, it asynchronously calls
the [PDF OCR Service](../pdf-ocr-service/README.md) to extract text and tracks the result as a
status.

## Tech stack

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA + Hibernate
- Flyway (schema migrations)
- Spring Validation
- Spring WebMVC
- H2 (file-based, for local development)

## Architecture

The domain is modelled as a `document` aggregate, split into three packages so that document
metadata, the (potentially large) file bytes, and the parsing result each persist and evolve
independently:

```
com.wiltech.pdfparser.document
├── Document                        entity — metadata (id, name, type, size, createdAt)
├── DocumentRepository               Spring Data repository for Document
├── DocumentDto / CreateDocumentRequest   API-facing DTOs
├── DocumentNotFoundException        maps to HTTP 404
├── DocumentApplicationService        CRUD over Document only
├── DocumentService                   orchestrates DocumentApplicationService,
│                                     DocumentContentApplicationService and
│                                     DocumentParsingService for operations that span more than
│                                     one aggregate (upload, delete, and building the full
│                                     DocumentDto for reads)
├── DocumentRestService                REST endpoints for document metadata
├── content
│   ├── DocumentContent                entity — blob storage, shares its primary key with
│   │                                  Document via a foreign key (document_id)
│   ├── DocumentContentRepository
│   ├── DocumentContentNotFoundException
│   ├── DocumentContentApplicationService   CRUD over DocumentContent only
│   └── DocumentContentRestService          REST endpoint for uploading a document's bytes
└── parsing
    ├── DocumentParsing                entity — status + parsed text, shares its primary key
    │                                  with Document via a foreign key (document_id)
    ├── DocumentParsingStatus           enum: FILE_UPLOADED, PROCESSING, OK, FAILED,
    │                                  INSUFFICIENT_DATA
    ├── DocumentParsingRepository
    ├── DocumentParsingNotFoundException
    ├── DocumentParsingRequestedEvent   published after an upload transaction commits
    └── DocumentParsingService          plain Service (not an ApplicationService) — CRUD over
                                        DocumentParsing, and calls the OCR service asynchronously
```

Each REST service delegates to an `*ApplicationService` for operations on a single aggregate
(`Document` or `DocumentContent`). Operations that touch more than one aggregate — uploading a
file, deleting a document, and assembling the full `DocumentDto` for reads (which needs both
`Document` and `DocumentParsing`) — go through the plain `DocumentService`, which coordinates the
application services and `DocumentParsingService` inside a single `@Transactional` boundary.

### How parsing works

1. `DocumentService.upload(...)` creates the `Document`, saves the content, and creates a
   `DocumentParsing` row with status `FILE_UPLOADED` — all in one transaction.
2. It publishes a `DocumentParsingRequestedEvent`. `DocumentParsingService` listens for it with
   `@TransactionalEventListener(phase = AFTER_COMMIT)` + `@Async`, so the HTTP call to the OCR
   service only starts after the upload transaction commits (avoiding a race where the async
   thread reads the `DocumentParsing` row before it exists) and runs on a separate thread (so the
   upload response isn't held up by parsing).
3. The listener sets status to `PROCESSING`, POSTs the bytes to `pdf-ocr-service` (`pdf.ocr.service-url`,
   defaults to `http://localhost:8000`, overridden by `PDF_OCR_SERVICE_URL` in Docker Compose),
   and on response sets status to `OK` (with the extracted text) or `INSUFFICIENT_DATA` (if the
   extracted text is blank). An exception (timeout, connection refused, etc.) sets `FAILED`.

## REST API

All endpoints are rooted at `/api/documents`.

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/documents`     | List all documents (metadata + parsing status/text) |
| `GET`    | `/api/documents/{id}` | Get a single document |
| `POST`   | `/api/documents`     | Upload a file (`multipart/form-data`, field name `file`). Metadata (name, content type, size) is derived from the uploaded file itself; kicks off async parsing |
| `DELETE` | `/api/documents/{id}` | Delete a document's metadata, stored bytes, and parsing result |

`DocumentDto` shape:

```json
{
  "id": "d99e7ef4-0e83-4f49-a2fb-15a192bdb3e9",
  "name": "invoice.pdf",
  "type": "application/pdf",
  "size": 1024,
  "createdAt": "2026-08-17T20:12:23.493853Z",
  "statusCode": "OK",
  "parsedText": "...extracted text..."
}
```

`statusCode` is `FILE_UPLOADED` right after upload, `PROCESSING` while the OCR call is in flight,
then `OK`, `INSUFFICIENT_DATA`, or `FAILED`. `parsedText` is `null` until status is `OK`.

## Database

Uses a file-based H2 database (`./data/pdfparser`) so data survives restarts during local
development. Flyway owns the schema (`src/main/resources/db/migration`); Hibernate is configured
with `ddl-auto: validate` and never generates DDL itself. The H2 console is available at
`/h2-console` while the app is running.

Tables:
- `documents` — id (UUID), name, type, size, created_at
- `document_content` — document_id (PK, FK to `documents.id`, cascades on delete), data (blob)
- `document_parsing` — document_id (PK, FK to `documents.id`, cascades on delete), status,
  parsed_text (clob)

## Running locally

Either directly with Maven:

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`. The Angular dev server proxies `/api/*` requests here
(see `PDF-parser-ui/proxy.conf.json`).

Or as part of the full stack via Docker Compose — see the [root README](../README.md) for that.

## Docker

`Dockerfile` is a two-stage build: `eclipse-temurin:25-jdk` builds the jar with the Maven wrapper,
`eclipse-temurin:25-jre` runs it. The H2 data directory (`/app/data`) is meant to be mounted as a
volume so data survives container rebuilds — see `docker-compose.yml` at the repo root.

## Status

Document metadata + content CRUD, async OCR parsing, and status tracking are implemented and
wired up to the UI end-to-end. Not yet implemented: retrying a failed/insufficient parse, and
downloading/viewing a document's raw stored bytes.
