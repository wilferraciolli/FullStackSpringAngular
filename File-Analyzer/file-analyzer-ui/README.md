# File Analyzer UI

The frontend for the File Analyzer application, built with Angular 22 and Angular Material.

## Features

- PDF file selection and upload.
- Upload progress tracking.
- Visual feedback using Angular Material components.
- Displays extracted data from the blood test.

## Tech Stack

- **Angular**: 22
- **UI Framework**: Angular Material
- **Styling**: SCSS
- **Package Manager**: npm

## Getting Started

### Prerequisites

- Node.js 22+
- npm

### Development server

Run `npm start` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `npm run build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Component Overview

- `App`: Main application container.
- `FileUploadComponent`: Handles file selection, upload logic, and displays the results.

## Service Overview

- `FileService`: Communicates with the Spring Boot backend (`http://localhost:8080/api/files`).
