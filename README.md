# Addresses Management API

The springboot application was successfully refactored from a monolithic controller-based design into a layered architecture with proper separation of concerns.

What this actually means

Originally, the application had a single layer doing too many things:

HTTP request handling
Business logic
Database access
Filtering and transformation

That is called a monolithic controller design.

After refactoring, I splited responsibilities into clear layers, where each layer has a single responsibility.

# 1. 🏗 Architecture Improvement
Before (Problem)

Everything was inside the controller:

SQL queries in controller
Filtering logic in controller
Data mapping in controller

👉 This creates:

tight coupling
hard-to-test code
difficult maintenance
low scalability
After (Your current design)
Controller

Handles only HTTP concerns:

request mapping
response status
Service Layer

Handles business logic:

validation rules (beyond annotations)
orchestration
exception handling decisions
Repository Layer

Handles data access only:

SQL queries (JdbcTemplate)
DB mapping
Why this is better

✔ Each layer has one responsibility
✔ Easier to test (mock repository in service tests)
✔ Easier to change DB logic without touching business logic
✔ Cleaner separation of concerns

👉 This is a standard enterprise architecture pattern

# 2. 🗄 Data Access Improvement
Before
SQL written in controller
No abstraction
Filtering done in Java loops after fetching all data

Example problem:

getAllAddresses()
filter manually in memory
After

All DB logic is in:

AddressRepository

Now:

Controller never touches DB
Service never writes SQL
Why this matters
✔ Cleaner design

Database logic is isolated in one place.

✔ Easier migration

If tomorrow you switch from JDBC → JPA:

You ONLY change repository layer.

Service + controller remain untouched.

The repository abstraction ensures persistence logic is isolated, making future migration to JPA or another datastore non-invasive.

# 3. 🌐 API Improvements
Before
Non-standard routing
No versioning
Mixed patterns like:
/{userId}/addresses
/addresses
After

You standardized to:

/api/v1/addresses
/api/v1/addresses/{id}
/api/v1/addresses/user/{userId}
Why this is better
✔ RESTful structure

Resources are clearly defined (addresses)

✔ Versioning support
/v1 allows future changes without breaking clients
✔ Predictable API design

Frontend can easily consume endpoints

Key interview line:

“API versioning was introduced to ensure backward compatibility and support future evolution of the service.”

# 4. 🧾 Validation & Error Handling
Before
No validation guarantees
Invalid payloads could reach database
Default Spring errors exposed
After
Validation added:
@NotBlank
@NotNull

This ensures:

no empty required fields
type safety at request level
Global exception handler added:
@RestControllerAdvice

Centralized handling for:

validation errors
resource not found
type mismatches
database errors
Why this is important
✔ Consistent API behavior

Every error follows same format

✔ Better UX for clients

Frontend receives structured errors

✔ Security improvement

No internal stack traces exposed

Key interview line:

“A centralized exception handling mechanism was introduced to standardize error responses and improve API reliability.”

# 5. 🚀 Deployment Readiness
Before
Only local Spring Boot app
Manual DB setup required
After
Docker added
Application containerized
Reproducible builds
Docker Compose added

Includes:

Spring Boot app
MySQL database
Why this matters
✔ Environment consistency

Works same everywhere (dev, QA, prod)

✔ Easy onboarding

New developers just run:

docker-compose up
✔ Production readiness

Matches real-world deployment patterns

Environment externalization

Database config moved to:

SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
