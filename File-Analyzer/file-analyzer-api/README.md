# File Analyzer API

The backend for the File Analyzer application, built with Spring Boot 4 and Java 25.

## Features

- PDF text extraction using Apache PDFBox.
- JSON transformation of extracted data.
- Persistence in PostgreSQL.
- Database migrations with Flyway.
- RESTful API for file uploads.

## Tech Stack

- **Java**: 25
- **Spring Boot**: 4.1.0
- **Database**: PostgreSQL
- **Migration**: Flyway
- **Library**: Apache PDFBox 3.0.0
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- JDK 25
- Maven
- A running PostgreSQL instance (manually or via `docker compose -f docker-compose.dev.yml up -d`)

### Configuration

The application is configured via `src/main/resources/application.yaml`. It uses environment variables with defaults:

- `SPRING_DATASOURCE_URL`: `jdbc:postgresql://localhost:5432/file_analyzer`
- `SPRING_DATASOURCE_USERNAME`: `user`
- `SPRING_DATASOURCE_PASSWORD`: `password`

### Running locally

```bash
mvn spring-boot:run
```

## API Endpoints

### File Upload

`POST /api/files/upload`

- **Content-Type**: `multipart/form-data`
- **Body**: `file` (PDF file)
- **Response**: `200 OK` with the `ProcessedFile` object in JSON format.

## Database Schema

The table `processed_files` includes:
- `id`: UUID
- `file_name`: String
- `content_type`: String
- `size`: Long
- `raw_text`: TEXT (extracted text)
- `extracted_data`: JSONB (structured data)
- `processed_at`: Timestamp
