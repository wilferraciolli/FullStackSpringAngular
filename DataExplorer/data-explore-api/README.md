# data-explore-api

Spring Boot 4 GraphQL API powering the Data Explorer application. Exposes employee and address data through a fully-typed GraphQL schema with server-side filtering, sorting, pagination, and an AI natural-language query parser endpoint.

---

## Table of Contents

- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Configuration & Environment](#configuration--environment)
- [GraphQL API](#graphql-api)
- [AI Query Parser](#ai-query-parser)
- [Database](#database)
- [Project Structure](#project-structure)
- [Interactive Tools](#interactive-tools)
- [Troubleshooting](#troubleshooting)

---

## Overview

The API serves two data areas — **Person** and **Address** — backed by database views on top of a normalised schema. Every query goes through JPA Specifications so filtering, sorting and pagination are handled entirely by the database (no in-memory loops).

Key capabilities:

| Feature | Detail |
|---|---|
| **GraphQL endpoint** | `POST /graphql` — all data queries go here |
| **Pagination** | Every query returns a `Page` wrapper with `totalElements`, `totalPages`, `page`, `size` |
| **Server-side filtering** | `StringFilter` (equals, contains, startsWith, notEquals, isNull) and `DateFilter` (after, before) pushed to SQL WHERE clause via JPA Specifications |
| **Sorting** | `ASC`/`DESC` on any indexed field |
| **AI parse endpoint** | `POST /api/ai/parse-query` — converts a plain-English prompt into a structured query using Groq (Llama 3.1) |
| **GraphiQL playground** | `GET /graphiql` — in-browser schema explorer and query tester |
| **H2 console** | `GET /h2-console` — in-browser SQL console |

---

## Technology Stack

| Layer | Technology | Version |
|---|---|---|
| Framework | Spring Boot | 4.0.6 |
| Language | Java | 25 |
| API protocol | Spring GraphQL | (Boot-managed) |
| ORM | Spring Data JPA / Hibernate | (Boot-managed) |
| Filtering | JPA Criteria API (`Specification<T>`) | — |
| Database | H2 in-memory | — |
| Migrations | Flyway | (Boot-managed) |
| Code generation | Lombok | — |
| AI client | Spring `RestClient` → Groq API | — |
| Build tool | Maven (wrapper included) | — |

---

## Getting Started

### Prerequisites

- Java 25+ (or 21+ with minor version change in `pom.xml`)
- Maven 3.8+ (or use the included `mvnw` / `mvnw.cmd` wrapper)

### Run

```bash
# Windows
mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

API starts on **http://localhost:8080**.

### Build

```bash
mvnw.cmd clean package          # Windows
./mvnw clean package            # Mac / Linux
```

---

## Configuration & Environment

### `application.yml`

| Property | Default | Description |
|---|---|---|
| `ai.groq.api-key` | `${GROQ_API_KEY:}` | Groq API key — loaded from `.env` |
| `ai.groq.url` | `https://api.groq.com/openai/v1/chat/completions` | Groq endpoint |
| `ai.groq.model` | `llama-3.1-8b-instant` | Model used for query parsing |
| `ai.groq.trust-all-certs` | `${AI_GROQ_TRUST_ALL_CERTS:false}` | Set `true` behind a corporate SSL-inspecting firewall |

### `.env` file

Copy `.env.example` to `.env` (already git-ignored) and fill in your values:

```bash
# Get a free key at https://console.groq.com → API Keys
GROQ_API_KEY=gsk_your_key_here

# Set true if you see PKIX / certificate_unknown errors (corporate firewall SSL inspection)
AI_GROQ_TRUST_ALL_CERTS=false
```

> **Note:** Spring Boot loads `.env` via `spring.config.import`. If your IntelliJ working directory is the project root rather than the module root, the service also reads both paths manually as a fallback. The startup log always prints whether the key was found and from where.

### IntelliJ Working Directory (recommended fix)

Edit Configurations → Working directory → set to `$MODULE_WORKING_DIR$`
This makes Spring Boot find `.env` automatically without the manual fallback.

---

## GraphQL API

**Endpoint:** `POST http://localhost:8080/graphql`
**Playground:** `GET http://localhost:8080/graphiql`

### Schema Summary

```graphql
type Query {
    people(filter: PersonFilter, sort: PersonSort, page: PageInput): PersonPage
    addresses(filter: AddressFilter, sort: AddressSort, page: PageInput): AddressPage
}

# Pagination input
input PageInput {
    page: Int   # 0-based page number
    size: Int   # rows per page
}

# Paginated result wrappers
type PersonPage {
    content:       [Person]
    totalElements: Int
    totalPages:    Int
    page:          Int
    size:          Int
}

# String field filter — all operators are optional; first non-null wins
input StringFilter {
    equals:     String
    notEquals:  String
    contains:   String
    startsWith: String
    isNull:     Boolean
}

# Date field filter (ISO format: YYYY-MM-DD)
input DateFilter {
    after:  String   # inclusive >=
    before: String   # inclusive <=
}
```

### Person Fields

| Field | Type | Filterable |
|---|---|---|
| `id` | ID | ✅ |
| `firstName` | String | ✅ |
| `lastName` | String | ✅ |
| `email` | String | ✅ |
| `jobTitle` | String | ✅ |
| `startDate` | Date | ✅ |
| `effectiveDate` | Date | ✅ (date-range only) |
| `phoneNumber` | String | — |
| `bio` | String | — |

### Address Fields

| Field | Type | Filterable |
|---|---|---|
| `city` | String | ✅ |
| `street` | String | ✅ |
| `effectiveDate` | Date | ✅ (date-range only) |

### Example Queries

**People with email containing "hotmail", page 1:**
```graphql
{
  people(
    filter: { email: { contains: "hotmail" }, effectiveDate: { after: "1900-01-01", before: "2099-12-31" } }
    sort:   { field: lastName, order: ASC }
    page:   { page: 0, size: 20 }
  ) {
    content { firstName lastName email jobTitle }
    totalElements
    totalPages
    page
  }
}
```

**Addresses in a specific city:**
```graphql
{
  addresses(
    filter: { city: { equals: "London" } }
    page:   { page: 0, size: 50 }
  ) {
    content { city street }
    totalElements
  }
}
```

### How Filtering Works

Filtering uses **JPA Specifications** (`Specification<T>`). Each filter field generates a SQL predicate via `CriteriaBuilder` (LIKE, =, IS NULL, >=, <=). Predicates are ANDed together and passed to `repository.findAll(spec, pageable)` — the database executes a single `SELECT … WHERE … LIMIT ? OFFSET ?` query. No data is ever loaded into memory and then filtered in Java.

---

## AI Query Parser

**Endpoint:** `POST http://localhost:8080/api/ai/parse-query`
**Status check:** `GET http://localhost:8080/api/ai/status`

Converts a plain-English user prompt into a structured query object that the Angular UI can apply directly to the query builder state. Called automatically by the frontend when Chrome's built-in AI (Gemini Nano) is unavailable.

### Request

```json
{
  "prompt": "people with email containing hotmail.com",
  "schema": [
    {
      "key": "person",
      "label": "Person",
      "fields": [
        { "key": "person.email", "label": "Email", "type": "String", "filterable": true },
        { "key": "person.firstName", "label": "First Name", "type": "String", "filterable": true }
      ]
    }
  ]
}
```

### Response

```json
{
  "area": "person",
  "fieldKeys": ["person.firstName", "person.lastName", "person.email"],
  "filters": [
    { "fieldKey": "person.email", "operator": "contains", "value": "hotmail.com" }
  ]
}
```

### How it Works

1. The frontend sends the user's prompt alongside the live schema (areas and filterable fields already loaded via introspection).
2. `AiService` builds a system prompt that injects the exact field keys so the model cannot hallucinate field names.
3. The Groq API (`llama-3.1-8b-instant`) returns a JSON object.
4. The response is deserialized into `ParsedQueryResponse` and returned to the frontend.

See `src/main/resources/testing/ai.http` for ready-to-run test requests.

---

## Database

| Item | Detail |
|---|---|
| Engine | H2 in-memory (resets on restart) |
| Console | http://localhost:8080/h2-console |
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(blank)* |

### Migrations (Flyway)

| File | What it does |
|---|---|
| `V001__Initial_schema_inflation.sql` | Core schema: employees, jobs, addresses |
| `V002__Create_entities_tables.sql` | Additional entity tables |
| `V003__Insert_sample_data.sql` | ~600 employees, ~1700 occupancy rows via `generate_series` |
| `R__Create_views.sql` | `person_view` and `address_view` used by GraphQL resolvers |

The GraphQL resolvers query the **views** (`person_view`, `address_view`) rather than the raw tables. This cleanly separates the reporting layer from the storage schema.

---

## Project Structure

```
src/main/java/com/witech/dataexplore/
├── ai/
│   ├── AiController.java          # POST /api/ai/parse-query, GET /api/ai/status
│   ├── AiService.java             # Groq API client + .env loader + trust-all SSL
│   ├── ParseQueryRequest.java     # Request DTO (prompt + schema)
│   └── ParsedQueryResponse.java   # Response DTO (area + fieldKeys + filters)
├── graphql/
│   ├── GraphQLController.java     # @QueryMapping for people + addresses
│   ├── PageInput.java             # { page, size }
│   ├── PageResult.java            # Generic page wrapper T
│   ├── SortOrder.java             # ASC / DESC enum
│   ├── helpers/
│   │   └── SpecificationHelper.java  # stringFilter(), dateFilter(), uuidStringFilter()
│   ├── person/
│   │   ├── PersonView.java           # @Entity mapped to person_view
│   │   ├── PersonFilter.java         # GraphQL filter input DTO
│   │   ├── PersonSort.java           # Sort field enum + order
│   │   ├── PersonViewRepository.java # extends JpaSpecificationExecutor
│   │   ├── PersonViewService.java    # findAll(filter, sort, page)
│   │   └── PersonViewSpec.java       # Chains filter fields into Specification<PersonView>
│   └── addresses/                    # Mirror of person/ for addresses
└── DataExploreApiApplication.java
```

---

## Interactive Tools

| Tool | URL | Purpose |
|---|---|---|
| GraphiQL Playground | http://localhost:8080/graphiql | Write and test GraphQL queries interactively |
| H2 Console | http://localhost:8080/h2-console | Run SQL directly against the in-memory DB |
| AI Status | http://localhost:8080/api/ai/status | Verify Groq key is loaded |

---

## Troubleshooting

**`[AI] GROQ_API_KEY not found`**
Check the startup log for `Working directory searched:`. Either:
- Add the key to `.env` in that directory, or
- Set `GROQ_API_KEY` directly in IntelliJ → Edit Run Configuration → Environment variables

**`PKIX path building failed` (SSL error)**
Your corporate firewall is intercepting HTTPS. Set `AI_GROQ_TRUST_ALL_CERTS=true` in `.env`.

**Empty GraphQL results**
Open the H2 console and run `SELECT COUNT(*) FROM person_view;`. If 0, check Flyway migration logs on startup.

**Port 8080 in use**
```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```
