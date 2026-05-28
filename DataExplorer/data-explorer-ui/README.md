# data-explorer-ui

Angular 21 front-end for the Data Explorer application. Provides a guided, no-code interface for building and running GraphQL queries against the Spring Boot API — complete with AI-powered natural-language query building, pagination, date-range filtering, field-level filters, and full-dataset CSV export.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Application Flow](#application-flow)
- [Architecture](#architecture)
- [Key Concepts](#key-concepts)
- [AI Query Builder](#ai-query-builder)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)

---

## Overview

Data Explorer UI introspects the GraphQL schema at startup to auto-discover all available data areas and their fields — no hardcoding required. Users pick an area, choose fields, optionally describe what they want in plain English, and run the query. Results appear in an AG Grid table with pagination controls and a one-click export to CSV (all pages, not just the current view).

---

## Features

| Feature | How it works |
|---|---|
| **Schema introspection** | On load, the app queries `__schema` to discover areas, fields, types, and which fields are filterable — zero configuration needed when new areas are added to the API. |
| **AI query builder** | Type a plain-English request (e.g. *"people with email containing hotmail.com"*). Uses **Chrome AI (Gemini Nano)** if available — fully on-device, no internet, no API key. Falls back to **Groq API** via the Spring Boot backend. |
| **Field selection** | Pick any combination of fields from the selected area. Fields are shown as drag-and-drop chips so you can reorder the output columns. |
| **Date range** | Separate filter panel with preset options (Today / This Week / This Month / Past Year / Custom date-picker). Sent as `effectiveDate.after` / `effectiveDate.before` on every query. |
| **Field filters** | Search any filterable field and add condition rows with operators: `equals`, `not equals`, `contains`, `starts with`, `is null`, `is not null`, `after` (dates), `before` (dates). |
| **Server-side pagination** | `page` / `size` args are sent to the API and the database handles `LIMIT`/`OFFSET`. Prev/Next controls and total row count displayed in the results toolbar. |
| **Configurable page size** | Dropdown in the Date Range panel: 10 / 20 / 50 / 100 rows per page. |
| **CSV export (all pages)** | Re-fires the current query with `pageSize: 10 000` to fetch all matching rows, builds a quoted CSV in-browser, and triggers a file download — no extra backend endpoint needed. |
| **Saved queries** | Save any query with a name for quick re-use via the Query Manager. |

---

## Getting Started

### Prerequisites

- Node.js 20+
- npm 10+

### Install and run

```bash
npm install
npm start
```

Navigate to **http://localhost:4200**. The API must be running on `http://localhost:8080`.

### Build for production

```bash
npm run build
```

Output goes to `dist/data-explorer-ui`.

### Run tests

```bash
npm test
```

---

## Application Flow

```
User opens app
    │
    ▼
Schema introspection (GET /graphql with __schema query)
    │   Discovers: areas, fields, types, filterable fields
    ▼
Home Page → Open Query Manager
    │
    ├─── Query Viewer (browse / reopen saved queries)
    │
    └─── Query Builder
             │
             ├─ 1. Select data area  (Person / Address / …)
             ├─ 2. Tick fields       (or type a prompt → AI fills everything)
             ├─ 3. Set date range    (Today / This Week / Custom / None)
             ├─ 4. Add filters       (field + operator + value rows)
             │
             ├─── [ Save ] → stored in Query Manager
             │
             └─── [ Run Query ]
                      │
                      ▼
                  GraphQL POST to /graphql
                      │
                      ▼
                  AG Grid results table
                      │
                      ├─ Prev / Next page buttons
                      └─ Export CSV (re-queries all pages)
```

---

## Architecture

### Component tree

```
AppComponent  (shell, toolbar, router-outlet)
│
├── HomeComponent              landing page
│
└── QueryManagerComponent      hosts the split-pane layout
        │
        ├── QueryViewerComponent      browse + search saved queries
        │
        └── QueryBuilderComponent     main builder UI
                │
                ├── Data Fields panel     (area selector + field checkboxes)
                │
                ├── Query accordion
                │     ├── ① Selected Fields   (CDK drag-drop chip list)
                │     ├── ② Date Range        (preset select + Material date-range-picker)
                │     └── ③ Filters           (autocomplete search + condition rows)
                │
                └── QueryResultsComponent
                          │
                          └── AG Grid (community)  + pagination toolbar + export button
```

### State management — NgRx Signals (`SchemaStore`)

`SchemaStore` is a `signalStore` that owns the shared state for the entire query lifecycle:

| Signal / method | Purpose |
|---|---|
| `dataAreas` | Discovered areas and their fields (loaded once at startup) |
| `isLoading` | True while the schema introspection is in flight |
| `queryResults` | Raw GraphQL response object |
| `isExecuting` | True while a query is running |
| `totalElements` / `totalPages` / `currentPage` | Pagination metadata |
| `lastPayload` | The last `QueryPayload` (fields, filters, date range, page size) — re-used by `changePage()` and `exportAll()` |
| `runQuery(payload)` | Fires a new query and resets to page 0 |
| `changePage(n)` | Re-fires `lastPayload` with a new page number |

### GraphQL service (`GraphqlService`)

- **`extractDataAreasFromSchema()`** — runs an introspection query, navigates `PersonPage → content → [Person]` to unwrap the pagination wrappers, and marks each field as `filterable` based on whether it appears in the corresponding `*Filter` input type.
- **`buildGraphQLQuery()`** — assembles the GraphQL query string from the payload: groups fields by area, builds filter args, always sends `effectiveDate` range, adds `page { page size }`, and wraps field selections in `content { … }`.
- **`executeQuery()`** — POSTs the query and throws if the response contains GraphQL errors.

---

## Key Concepts

### Filterable vs all fields

The schema introspection checks each field name against the backend `*Filter` input type (e.g. `PersonFilter`). Only fields that exist in the filter input are marked `filterable: true`. The filter autocomplete shows only filterable fields. `effectiveDate` is always excluded from field filters (it is handled by the Date Range panel).

### Date range

Every query sends `effectiveDate: { after: "…", before: "…" }`. When the Date Range panel is set to `None`, the defaults `1900-01-01` / `2099-12-31` are used, which effectively means "no date restriction". The date formatter uses local date arithmetic (not `toISOString()`) to avoid timezone shifts.

### Pagination

Page size is chosen before running the query (10 / 20 / 50 / 100). The `page` arg is always sent so the database applies `LIMIT`/`OFFSET`. `SchemaStore.changePage(n)` stores `lastPayload` so filter/sort/field state is preserved when navigating between pages.

### CSV export

The **Export CSV** button in the results toolbar calls `store.exportAll()`:
1. Takes `lastPayload` (current filters, fields, date range).
2. Re-fires `executeQuery` with `pageSize: 10 000` and `page: 0`.
3. Extracts `content[]` from the response.
4. Builds a CSV string (quoted cells, embedded quotes escaped).
5. Creates a `Blob` → `<a download>` → programmatic click → browser saves the file.

---

## AI Query Builder

The **Build with AI** button in the top toolbar accepts a plain-English description and automatically selects the area, fields, and filter rows.

### How the AI strategy works

```
User types prompt
        │
        ▼
Is window.LanguageModel available AND status === 'ready'?
        │                │
       YES              NO
        │                │
        ▼                ▼
  Chrome AI         POST /api/ai/parse-query
  (Gemini Nano)     (Spring Boot → Groq API)
  on-device,        requires GROQ_API_KEY in .env
  no internet
        │                │
        └──────┬──────────┘
               ▼
      ParsedQuery { area, fieldKeys[], filters[] }
               │
               ▼
      applyParsedQuery() → sets area, fields, filter rows
```

### Chrome AI (Gemini Nano) — zero-cost, on-device

Badge states shown next to the button:

| Badge | Meaning |
|---|---|
| 🟢 **On-device** | Gemini Nano is downloaded and ready |
| 🟡 **Model not yet downloaded — using Groq** | Chrome AI supported but model needs a download |
| *(no badge)* | Chrome AI unavailable — Groq API used |

**To enable Gemini Nano:**
1. Open Chrome and go to `chrome://flags/#optimization-guide-on-device-model`
2. Set the flag to **Enabled BypassPerfRequirement**
1.1. Open Chrome and go to `chrome://flags/#prompt-api-for-gemini-nano`
2.1. Set the flag to **Enabled**
3. Click **Relaunch**
4. Chrome downloads Gemini Nano (~1.7 GB) in the background
5. Track progress at `chrome://on-device-ai`
6. Reload this page once done

### Groq fallback

When Chrome AI is unavailable, the frontend sends the prompt + live schema to `POST /api/ai/parse-query` on the Spring Boot backend, which calls Groq's API. See the API README for setup instructions.

---

## Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Framework | Angular | 21.x |
| UI components | Angular Material | 21.x |
| Drag & drop | Angular CDK | 21.x |
| Data grid | AG Grid Community | 35.x |
| State management | NgRx Signals (`signalStore`) | 21.x |
| Styling | SCSS (BEM-style, component-scoped) | — |
| Language | TypeScript | ~5.9 |
| Build | Angular CLI / esbuild | 21.x |

---

## Project Structure

```
src/app/
├── app.ts / app.html / app.routes.ts   # Shell + routing
│
├── components/
│   ├── common/home/                    # Landing page
│   ├── query-builder/                  # Main builder UI + interfaces
│   │   ├── query-builder.ts            # Component logic + AI wiring
│   │   ├── query-builder.html          # Template
│   │   └── query-builder.scss
│   ├── query-manager/                  # Host layout (viewer + builder)
│   ├── query-viewer/                   # Browse saved queries
│   └── query-results/                  # AG Grid table + pagination + export
│       ├── query-results.ts
│       ├── query-results.html
│       └── query-results.scss
│
├── interfaces/
│   ├── query.interface.ts              # SavedQuery shape
│   └── category.interface.ts
│
└── services/
    ├── graph-ql.service.ts             # Schema introspection + query builder + executor
    ├── query-builder.store.ts          # NgRx Signal Store (shared state)
    └── ai-query.service.ts             # Chrome AI + Groq fallback + schema prompt builder
```

### Interfaces exported from `query-builder.ts`

| Interface / type | Purpose |
|---|---|
| `DataArea` | A queryable area: `key`, `label`, `icon`, `queryName`, `fields[]` |
| `DataField` | A field: `key`, `label`, `type`, `filterable` |
| `IndexedField` | `DataField` + `areaKey` + `areaLabel` (used in filter rows) |
| `QueryPayload` | What the store sends: `fieldKeys`, `filters`, `dateRange`, `page`, `pageSize` |
| `FieldFilter` | A single filter row: `fieldKey`, `operator`, `value` |
| `FilterOperator` | Union type of all valid operators |
| `DateRangeOption` | `null \| 'today' \| 'this_week' \| 'this_month' \| 'past_year' \| 'custom'` |
