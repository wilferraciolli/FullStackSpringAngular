# File Analyzer

A full-stack application for analyzing PDF files (specifically blood tests), extracting text data, and storing it as JSON in a PostgreSQL database.

## Architecture

- **Backend**: Spring Boot 4 (Java 25) with Apache PDFBox and Flyway.
- **Frontend**: Angular 22 with Angular Material.
- **Database**: PostgreSQL.
- **Orchestration**: Docker Compose.

## Getting Started

### Prerequisites

- Docker and Docker Desktop
- Java 25 SDK (for manual run)
- Node.js 22+ and npm (for manual run)
- Maven 3.9+ (for manual run)

### Running the Full Stack (Production-like)

To run the entire application (API, UI, and Database) using Docker:

```bash
docker compose up -d
```

- **UI**: http://localhost:80
- **API**: http://localhost:8080
- **Database**: Port 5432

*Note: If you run the full stack, the UI is mapped to port 80. Port 4200 is only used for local Angular development.*

### Running for Development (Manual API & UI)

If you want to run the API and UI manually for development while keeping the database in Docker:

1.  **Start the Database**:
    ```bash
    docker compose -f docker-compose.dev.yml up -d
    ```
2.  **Run the API**:
    ```bash
    cd file-analyzer-api
    mvn spring-boot:run
    ```
3.  **Run the UI**:
    ```bash
    cd file-analyzer-ui
    npm install
    npm start
    ```

- **UI**: http://localhost:4200
- **API**: http://localhost:8080
- **Database**: Port 5432

## Project Structure

- `file-analyzer-api`: Spring Boot backend.
- `file-analyzer-ui`: Angular frontend.
- `docker-compose.yml`: Full stack orchestration.
- `docker-compose.dev.yml`: Development database orchestration.
