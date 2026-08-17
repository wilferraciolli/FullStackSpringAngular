# PDF Parser UI

Angular frontend for [PDF Parser](../README.md). Lets you upload PDF documents, browse previously
uploaded ones, and view their parsing status and extracted text.

## Tech stack

- Angular 22 (standalone components, signals, `httpResource`, zoneless change detection)
- Angular Material
- TypeScript

## Pages

- **Documents** (`/`) — lists uploaded documents (name, type, size, upload date, parsing status),
  an "Upload document" button, and a delete action per row.
- **Document details** (`/documents/:id`) — a single document's metadata, its parsing status, and
  the "Parsed content" section (extracted text once parsing finishes), with a back button to the
  list.

## Architecture notes

- `src/app/documents/documents.service.ts` wraps the backend's `/api/documents` endpoints. The
  document list and single-document fetches use Angular's `httpResource` signal API; uploads and
  deletes are plain `HttpClient` calls (one-shot actions, not reactive fetches) followed by a
  `.reload()` of the relevant resource.
- Routes are lazy-loaded (`loadComponent`) and bind route params straight to component inputs via
  `withComponentInputBinding()`.
- `src/app/documents/parsing-status.ts` centralizes the `DocumentParsingStatus` union type plus
  the label/CSS class per status, used by both pages for the status badge.
- Parsing runs asynchronously on the backend, so both pages poll (`effect()` + `setTimeout`,
  re-scheduled via the effect's cleanup callback) while a document's status is `FILE_UPLOADED` or
  `PROCESSING`, and stop once it reaches a terminal status.

## Running locally

Either directly with the Angular CLI (the dev server proxies `/api/*` to `http://localhost:8080`
— see `proxy.conf.json`):

```bash
npm install
npm start
```

This expects `pdf-parser-api` to already be running on port 8080 (`./mvnw spring-boot:run` from
that project).

Or as part of the full stack via Docker Compose — see the [root README](../README.md).

## Docker

`Dockerfile` is a two-stage build: `node:22-alpine` runs `npm run build`, then `nginx:alpine`
serves the compiled static files. `nginx.conf` proxies `/api/*` to the `pdf-parser-api` container
over the Compose network and falls back to `index.html` for client-side routes.

`nginx.conf` also sets `client_max_body_size 50m` — nginx's default is 1MB, which is far below
`pdf-parser-api`'s own 50MB upload limit and would silently reject large documents with a 413
before they ever reached the API. Keep this in sync with `spring.servlet.multipart.max-file-size`
in `pdf-parser-api`'s `application.yml` if that limit ever changes.
