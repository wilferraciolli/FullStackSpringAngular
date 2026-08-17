# PDF Parser API

Spring Boot REST API for uploading PDF documents and managing their metadata and content. It's
the backend for the [PDF Parser](../README.md) project — the Angular UI talks to this API to
upload files and list previously uploaded documents. The [PDF OCR
Service](../pdf-ocr-service/README.md) handles extracting text from the uploaded PDFs (not yet
wired up — see Status below).

## Tech stack

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA + Hibernate
- Flyway (schema migrations)
- Spring Validation
- Spring WebMVC
- H2 (file-based, for local development)

## Architecture

The domain is modelled as a `document` aggregate, split into two packages so that document
metadata and the (potentially large) file bytes are persisted and evolve independently:

```
com.wiltech.pdfparser.document
├── Document                        entity — metadata (id, name, type, size, createdAt)
├── DocumentRepository               Spring Data repository for Document
├── DocumentDto / CreateDocumentRequest   API-facing DTOs
├── DocumentNotFoundException        maps to HTTP 404
├── DocumentApplicationService        CRUD over Document only
├── DocumentService                   orchestrates DocumentApplicationService +
│                                     DocumentContentApplicationService for operations that span
│                                     both (upload, delete)
├── DocumentRestService                REST endpoints for document metadata
└── content
    ├── DocumentContent                entity — blob storage, shares its primary key with
    │                                  Document via a foreign key (document_id)
    ├── DocumentContentRepository
    ├── DocumentContentNotFoundException
    ├── DocumentContentApplicationService   CRUD over DocumentContent only
    └── DocumentContentRestService          REST endpoint for uploading a document's bytes
```

Each REST service delegates to an `*ApplicationService` for operations on a single aggregate
(`Document` or `DocumentContent`). Operations that need to touch both — uploading a file (create
metadata + store bytes) and deleting a document (remove bytes + remove metadata) — go through the
plain `DocumentService`, which coordinates the two application services inside a single
`@Transactional` boundary.

## REST API

All endpoints are rooted at `/api/documents`.

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/documents`     | List all documents (metadata only) |
| `GET`    | `/api/documents/{id}` | Get a single document's metadata |
| `POST`   | `/api/documents`     | Upload a file (`multipart/form-data`, field name `file`). Metadata (name, content type, size) is derived from the uploaded file itself |
| `DELETE` | `/api/documents/{id}` | Delete a document's metadata and its stored bytes |

`DocumentDto` shape:

```json
{
  "id": "d99e7ef4-0e83-4f49-a2fb-15a192bdb3e9",
  "name": "invoice.pdf",
  "type": "application/pdf",
  "size": 1024,
  "createdAt": "2026-08-17T20:12:23.493853Z"
}
```

## Database

Uses a file-based H2 database (`./data/pdfparser`) so data survives restarts during local
development. Flyway owns the schema (`src/main/resources/db/migration`); Hibernate is configured
with `ddl-auto: validate` and never generates DDL itself. The H2 console is available at
`/h2-console` while the app is running.

Tables:
- `documents` — id (UUID), name, type, size, created_at
- `document_content` — document_id (PK, FK to `documents.id`, cascades on delete), data (blob)

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

Document metadata + content CRUD is implemented and wired up to the UI's upload flow. Not yet
implemented: calling the OCR service on upload, tracking parsing status, storing/returning parsed
text, and downloading/viewing a document's raw stored bytes.
