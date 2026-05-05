# Data Explore API

A modern Spring Boot REST and GraphQL API for exploring and managing user and employee data. This application provides comprehensive data exploration capabilities with dual interface support (REST and GraphQL) and an in-memory H2 database with console access.

## Project Overview

Data Explore API is a full-featured Spring Boot application (v4.0.6) built with Java 21 that offers:

- **RESTful API** for user management with full CRUD operations
- **GraphQL API** with advanced filtering and sorting capabilities
- **Interactive GraphQL Playground** (GraphiQL) for schema exploration and testing
- **H2 Console** for database inspection and SQL queries
- **Database Versioning** with Flyway migrations
- **Sample Data** for immediate exploration

## Technology Stack

- **Framework**: Spring Boot 4.0.6
- **Java Version**: 21
- **Database**: H2 (in-memory)
- **API Protocols**: REST + GraphQL
- **Code Generation**: Lombok
- **Database Migration**: Flyway
- **Build Tool**: Maven

## Prerequisites

- Java 21 or higher
- Maven 3.8.x or higher (or use the included Maven wrapper)

## Getting Started

### Build the Project

```bash
# On Windows
mvnw.cmd clean package

# On Mac/Linux
./mvnw clean package
```

### Run the Application

```bash
# On Windows
mvnw.cmd spring-boot:run

# On Mac/Linux
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### REST API Endpoints

#### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/users` | Create a new user |
| **GET** | `/users` | Retrieve all users |
| **GET** | `/users/{id}` | Get user by ID |
| **GET** | `/users/username/{username}` | Get user by username |
| **PUT** | `/users/{id}` | Update user by ID |
| **DELETE** | `/users/{id}` | Delete user by ID |
| **GET** | `/users/check/username/{username}` | Check if username exists |
| **GET** | `/users/check/email/{email}` | Check if email exists |

#### Example Requests

**Create a User:**
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "johndoe"
  }'
```

**Get All Users:**
```bash
curl http://localhost:8080/users
```

### GraphQL API

The GraphQL API is available at `http://localhost:8080/graphql`

#### Schema

**Query:**
```graphql
type Query {
    people(filter: PersonFilter, sort: PersonSort): [Person]
    addresses(filter: AddressFilter, sort: AddressSort): [Address]
}
```

**Types:**

```graphql
type Person {
    id: ID
    email: String
    firstName: String
    lastName: String
    phoneNumber: String
    bio: String
    jobName: String
    startDate: String
}

type Address {
    id: ID
    city: String
    street: String
}
```

**Filtering & Sorting:**

```graphql
input PersonFilter {
    firstName: String
    lastName: String
    email: String
    jobName: String
}

input PersonSort {
    field: PersonSortField
    order: SortOrder
}

enum PersonSortField {
    lastName
    firstName
    email
    jobName
}

enum SortOrder {
    ASC
    DESC
}
```

#### Example GraphQL Queries

**Get All People:**
```graphql
query {
  people {
    id
    firstName
    lastName
    email
    jobName
  }
}
```

**Get People with Filtering:**
```graphql
query {
  people(filter: { jobName: "Engineer" }) {
    id
    firstName
    lastName
    email
    jobName
    startDate
  }
}
```

**Get People with Sorting:**
```graphql
query {
  people(sort: { field: lastName, order: ASC }) {
    id
    firstName
    lastName
    email
  }
}
```

**Get Addresses:**
```graphql
query {
  addresses(filter: { city: "New York" }) {
    id
    street
    city
  }
}
```

## Interactive Tools

### GraphQL Playground (GraphiQL)

Access the interactive GraphQL playground to explore the schema and test queries:

**URL:** `http://localhost:8080/graphiql`

Features:
- Interactive query editor
- Real-time schema documentation
- Query history
- Variable support
- Response formatting

### H2 Database Console

Access the H2 database console for direct database interaction:

**URL:** `http://localhost:8080/h2-console`

**Connection Details:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (leave blank)

Features:
- Run SQL queries
- Inspect database schema
- View tables and data
- Execute database operations

## Database Structure

### Entities

1. **User** - User account information
2. **Employee** - Employee details and employment information
3. **EmployeeAddress** - Address information for employees
4. **Job** - Job titles and descriptions
5. **Occupancy** - Employment history and occupancy records

### Database Migrations

Database schema is managed using Flyway migrations located in `src/main/resources/db/migration/`:

- `V001__Initial_schema_inflation.sql` - Initial database setup
- `V002__Create_entities_tables.sql` - Entity tables creation
- `V003__Insert_sample_data.sql` - Sample data population
- `R__Create_views.sql` - Database views

## Project Structure

```
data-explore-api/
├── src/
│   ├── main/
│   │   ├── java/com/witech/dataexplore/
│   │   │   ├── controller/        # REST endpoints
│   │   │   ├── service/           # Business logic
│   │   │   ├── repository/        # Data access
│   │   │   ├── entity/            # JPA entities
│   │   │   ├── dto/               # Data transfer objects
│   │   │   ├── graphql/           # GraphQL resolvers
│   │   │   ├── views/             # View-related classes
│   │   │   └── DataExploreApiApplication.java
│   │   └── resources/
│   │       ├── application.yml    # Configuration
│   │       ├── graphql/
│   │       │   └── schema.graphqls  # GraphQL schema
│   │       └── db/migration/      # Flyway migrations
│   └── test/                      # Test files
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## Configuration

Key application properties from `application.yml`:

```yaml
spring:
  application:
    name: data-explore-api
  datasource:
    url: jdbc:h2:mem:testdb      # In-memory H2 database
  h2:
    console:
      enabled: true              # Enable H2 console
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  graphql:
    graphiql:
      enabled: true              # Enable GraphiQL playground
      path: /graphiql
    cors:
      allowed-origins: "*"       # CORS for frontend access
```

## Building and Testing

### Build with Maven

```bash
# Clean and package
mvnw.cmd clean package

# Run tests
mvnw.cmd clean test

# Skip tests during build
mvnw.cmd clean package -DskipTests
```

### Run Specific Tests

```bash
mvnw.cmd test -Dtest=DataExploreApiApplicationTests
```

## Troubleshooting

### H2 Console Not Accessible
- Verify that `spring.h2.console.enabled: true` is set in `application.yml`
- Check that the application is running on the correct port

### GraphQL Queries Returning Empty Results
- Verify data migrations have executed successfully
- Check the H2 console to confirm data exists in the database

### Port Already in Use
If port 8080 is already in use, you can change it:

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## Development

### Adding New REST Endpoints
1. Create a new controller in `src/main/java/com/witech/dataexplore/controller/`
2. Implement the endpoint with appropriate HTTP methods
3. Add corresponding service and repository methods

### Adding GraphQL Resolvers
1. Update the GraphQL schema in `src/main/resources/graphql/schema.graphqls`
2. Implement resolvers in `src/main/java/com/witech/dataexplore/graphql/`

### Database Migrations
1. Create new migration files in `src/main/resources/db/migration/`
2. Follow naming convention: `V###__description.sql` for versioned migrations
3. Flyway will automatically execute pending migrations on startup

## Dependencies

- `spring-boot-starter-graphql` - GraphQL support
- `spring-boot-starter-webmvc` - REST API support
- `spring-boot-starter-data-jpa` - Database access
- `spring-boot-starter-flyway` - Database migrations
- `h2` - In-memory relational database
- `lombok` - Code generation (getters, setters, builders)
- `spring-boot-devtools` - Development tools (hot reload)

## License

This project is part of the FullStackSpringAngular series.

## Support

For issues or questions, please check the application logs or access the GraphQL playground to test queries directly.

---

**Application Name:** data-explore-api  
**Version:** 0.0.1-SNAPSHOT  
**Java Version:** 21  
**Spring Boot Version:** 4.0.6
