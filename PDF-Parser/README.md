# PDF Parser

A full-stack application for uploading PDF documents and parsing them into structured data. When a
document isn't cleanly extractable as text (e.g. scanned pages), the request is handed off to a
background OCR service for higher-quality extraction.

## Architecture

| Component        | Tech                              | Responsibility                                                      |
|-------------------|------------------------------------|-----------------------------------------------------------------------|
| `pdf-parser-api`  | Spring Boot (Java 25)             | REST API for uploading PDFs, orchestrating parsing, persisting results |
| `PDF-parser-ui`   | Angular 22                        | UI for uploading documents and viewing parsed results                 |
| `pdf-ocr-service` | Python (planned)                  | Background OCR worker for documents that need image-based text extraction |

```
┌────────────┐      upload PDF       ┌──────────────────┐      needs OCR?       ┌──────────────────┐
│ Angular UI │  ───────────────────▶ │  Spring Boot API  │  ───────────────────▶ │ Python OCR Service│
└────────────┘  ◀─────────────────── └──────────────────┘  ◀─────────────────── └──────────────────┘
                  parsed results               │  persists
                                                 ▼
                                          ┌────────────┐
                                          │  Database  │
                                          └────────────┘
```

## Projects

### `pdf-parser-api`
Spring Boot service exposing REST endpoints to upload PDFs, track parsing status, and retrieve
parsed content. Uses Spring Data JPA + Flyway for persistence and migrations, and Bean Validation
for request validation.

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA
- Flyway
- Spring Validation
- Spring WebMVC

### `PDF-parser-ui`
Angular application providing the upload UI and a view for browsing previously uploaded/parsed
documents.

- Angular 22
- TypeScript

### `pdf-ocr-service` (planned)
Python service that performs OCR on documents the API can't parse cleanly as native text (e.g.
scanned/image-based PDFs). Runs as a separate background service and communicates with the API.

## Status

Early scaffolding stage — API and UI projects are initialized but not yet implemented. The OCR
service has not been created yet.

## Getting Started

### API
```bash
cd pdf-parser-api
./mvnw spring-boot:run
```

### UI
```bash
cd PDF-parser-ui
npm install
npm start
```

### OCR Service
Not yet implemented.
