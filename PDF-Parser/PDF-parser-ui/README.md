# PDF Parser UI

Angular frontend for [PDF Parser](../README.md). Lets you upload PDF documents, browse previously
uploaded ones, and (once wired up) view their parsed text.

## Tech stack

- Angular 22 (standalone components, signals, `httpResource`, zoneless change detection)
- Angular Material
- TypeScript

## Pages

- **Documents** (`/`) — lists uploaded documents (name, type, size, upload date), an "Upload
  document" button, and a delete action per row.
- **Document details** (`/documents/:id`) — a single document's metadata plus a "Parsed content"
  section (placeholder until OCR parsing is wired up on the backend), with a back button to the
  list.

## Architecture notes

- `src/app/documents/documents.service.ts` wraps the backend's `/api/documents` endpoints. The
  document list and single-document fetches use Angular's `httpResource` signal API; uploads and
  deletes are plain `HttpClient` calls (one-shot actions, not reactive fetches) followed by a
  `.reload()` of the relevant resource.
- Routes are lazy-loaded (`loadComponent`) and bind route params straight to component inputs via
  `withComponentInputBinding()`.

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
