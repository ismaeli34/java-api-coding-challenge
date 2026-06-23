# Addresses Management API

This project started as a simple Spring Boot application where most of the functionality was implemented directly inside the controller layer. While the application worked, the controller was responsible for handling HTTP requests, business logic, data filtering, and database operations, making the code difficult to maintain and extend.

To improve the overall design, the application was refactored into a layered architecture with a clear separation of responsibilities. This makes the codebase easier to understand, test, and scale as the application grows.

## Architecture Improvements

### Before Refactoring

The controller handled almost everything:

* Request handling
* Business logic
* Database queries
* Data filtering and transformation

This approach resulted in tightly coupled code that became harder to test and maintain over time.

### After Refactoring

The application now follows a layered architecture:

#### Controller Layer

Responsible only for HTTP-related concerns:

* Request mapping
* Request/response handling
* Returning appropriate HTTP status codes

#### Service Layer

Responsible for business logic:

* Business rules and validations
* Coordination between components
* Exception handling decisions

#### Repository Layer

Responsible for data access:

* Database queries using `JdbcTemplate`
* Mapping database records to domain objects
* Isolating persistence logic from the rest of the application

### Benefits

* Clear separation of concerns
* Improved maintainability
* Easier unit testing through mocking
* Better scalability as new features are added
* Database implementation can be changed without impacting business logic

This structure follows common enterprise application design principles and makes the codebase significantly easier to manage.

---

## Data Access Improvements

### Before Refactoring

Database operations were performed directly inside the controller, and filtering was often done in memory after retrieving all records.

For example:

* Fetch all addresses
* Apply filtering using Java collections

This approach was inefficient and mixed persistence logic with API concerns.

### After Refactoring

All database interactions were moved to the `AddressRepository`.

As a result:

* Controllers no longer interact directly with the database
* Services no longer contain SQL statements
* Data access logic is centralized in a single layer

### Benefits

* Cleaner and more organized code
* Better separation between business and persistence concerns
* Easier maintenance of SQL queries
* Simplified migration to another persistence technology such as JPA

Because persistence logic is isolated within the repository layer, future database-related changes can be made with minimal impact on the rest of the application.

---

## API Design Improvements

### Before Refactoring

The API used inconsistent endpoint structures such as:

```text
/{userId}/addresses
/addresses
```

### After Refactoring

Endpoints were standardized and versioned:

```text
/api/v1/addresses
/api/v1/addresses/{id}
/api/v1/addresses/user/{userId}
```

### Benefits

* Consistent and predictable API design
* Improved RESTful resource organization
* Easier integration for frontend applications
* API versioning support for future enhancements

Introducing `/v1` ensures backward compatibility when future API versions are introduced.

---

## Validation and Error Handling

### Validation

Request validation was added using Bean Validation annotations such as:

* `@NotBlank`
* `@NotNull`

This helps ensure invalid requests are rejected before reaching the business or database layers.

### Global Exception Handling

A centralized exception handling mechanism was implemented using:

```java
@RestControllerAdvice
```

This provides consistent handling for:

* Validation failures
* Resource not found exceptions
* Invalid request parameters
* Database-related errors
* Unexpected application exceptions

### Benefits

* Consistent API responses
* Improved client experience
* Easier debugging
* Better security by preventing internal implementation details from being exposed

Clients now receive structured and predictable error responses across the entire application.

---

## Deployment and Environment Management

### Containerization

The application was containerized using Docker to ensure consistent execution across different environments.

### Docker Compose

Docker Compose was introduced to simplify local development and deployment by orchestrating:

* Spring Boot application
* MySQL database

Developers can start the entire environment with a single command:

```bash
docker-compose up
```

### Environment Configuration

Sensitive configuration values were externalized using environment variables:

```properties
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

### Benefits

* Consistent behavior across development, testing, and production environments
* Simplified onboarding for new developers
* Improved deployment process
* Better configuration management and security practices

---

## Summary

The application was successfully transformed from a controller-centric design into a clean, layered architecture following industry-standard Spring Boot development practices.

Key improvements include:

* Layered architecture (Controller → Service → Repository)
* Separation of concerns
* Centralized data access
* RESTful API design with versioning
* Request validation and global exception handling
* Docker-based deployment setup
* Environment-based configuration management

These changes significantly improve maintainability, testability, scalability, and overall production readiness of the application.



<img width="902" height="741" alt="Screenshot 2026-06-22 at 15 09 31" src="https://github.com/user-attachments/assets/8d78c455-196e-4754-94aa-a0a65e570bd7" />
<img width="903" height="734" alt="Screenshot 2026-06-22 at 15 09 40" src="https://github.com/user-attachments/assets/7bc8eb2a-43f7-4744-af64-d3ff392b2e6e" />
<img width="899" height="715" alt="Screenshot 2026-06-22 at 15 09 50" src="https://github.com/user-attachments/assets/ff0a71f3-a032-49fe-9a14-bd9fa82c0207" />
<img width="901" height="716" alt="Screenshot 2026-06-22 at 15 10 01" src="https://github.com/user-attachments/assets/6fc1e2ca-2eba-45c8-bf5b-0a743825b2ad" />
<img width="899" height="712" alt="Screenshot 2026-06-22 at 15 10 45" src="https://github.com/user-attachments/assets/bdd1236b-2fa1-493c-8f3e-2fea27c3dc75" />
<img width="904" height="718" alt="Screenshot 2026-06-22 at 15 10 57" src="https://github.com/user-attachments/assets/3ea892a3-0fb5-41b6-994f-db547a5413ac" />
