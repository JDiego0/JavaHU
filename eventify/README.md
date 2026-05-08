# Eventify API

A Spring Boot application for managing events and venues. This project implements a clean architecture following Spring MVC patterns with in-memory data storage.

## Features

- **Event Management**: Create, read, update, and delete events
- **Venue Management**: Create, read, update, and delete venues
- **RESTful API**: Full REST endpoints with proper HTTP status codes
- **Swagger Documentation**: Interactive API documentation
- **Unit Testing**: Comprehensive test coverage with JUnit 5 and Mockito
- **Validation**: Business logic validation for all entities

## Technologies Used

- **Java 21**
- **Spring Boot 2.7.18**
- **Spring Web MVC**
- **Lombok** - For reducing boilerplate code
- **SpringDoc OpenAPI** - For Swagger documentation
- **JUnit 5** - For unit testing
- **Mockito** - For mocking in tests

## Project Structure

```
src/
├── main/
│   └── java/com/riwi/eventify/
│       ├── controllers/     # REST Controllers
│       ├── services/        # Business Logic Layer
│       ├── repositories/    # Data Access Layer (In-memory)
│       ├── models/          # Entity Classes
│       ├── config/          # Configuration Classes
│       └── EventifyApplication.java
└── test/
    └── java/com/riwi/eventify/
        └── services/        # Service Layer Tests
```

## API Endpoints

### Events
- `GET /api/events` - Get all events
- `POST /api/events` - Create a new event
- `GET /api/events/{id}` - Get event by ID
- `DELETE /api/events/{id}` - Delete event by ID

### Venues
- `GET /api/venues` - Get all venues
- `POST /api/venues` - Create a new venue
- `GET /api/venues/{id}` - Get venue by ID
- `DELETE /api/venues/{id}` - Delete venue by ID

## Data Models

### Event
```json
{
  "id": "long",
  "name": "string",
  "description": "string",
  "date": "datetime",
  "venue": "string"
}
```

### Venue
```json
{
  "id": "long",
  "name": "string",
  "address": "string",
  "capacity": "integer"
}
```

## Validation Rules

### Event Validation
- Name: Required, cannot be empty
- Date: Required
- Venue: Required, cannot be empty

### Venue Validation
- Name: Required, cannot be empty
- Address: Required, cannot be empty
- Capacity: Required, must be greater than 0

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd eventify
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=EventServiceTest
mvn test -Dtest=VenueServiceTest
```

## API Documentation

Once the application is running, you can access the interactive API documentation at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Usage Examples

### Create an Event
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rock Concert",
    "description": "Amazing rock concert",
    "date": "2024-06-15T20:00:00",
    "venue": "National Stadium"
  }'
```

### Create a Venue
```bash
curl -X POST http://localhost:8080/api/venues \
  -H "Content-Type: application/json" \
  -d '{
    "name": "National Stadium",
    "address": "123 Main Street",
    "capacity": 50000
  }'
```

### Get All Events
```bash
curl -X GET http://localhost:8080/api/events
```

### Get All Venues
```bash
curl -X GET http://localhost:8080/api/venues
```

## Configuration

### Data Seeding

The application includes optional data seeding. To enable it, add the following to your `application.properties`:

```properties
app.seed.enabled=true
```

When enabled, the application will pre-populate with sample events and venues on startup.

## Architecture

This project follows a clean layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic and validation
3. **Repository Layer**: Manages data storage (currently in-memory)
4. **Model Layer**: Defines entity structures

All layers use constructor dependency injection for better testability and maintainability.

## Testing

The project includes comprehensive unit tests for the service layer:

- **EventServiceTest**: 9 test cases covering all event operations
- **VenueServiceTest**: 12 test cases covering all venue operations

Tests use Mockito for mocking dependencies and follow the AAA pattern (Arrange, Act, Assert).

## Error Handling

The API returns appropriate HTTP status codes:
- `201 Created`: Successful resource creation
- `200 OK`: Successful read operations
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Resource not found
- `204 No Content`: Successful deletion

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run all tests to ensure they pass
6. Submit a pull request

## License

This project is licensed under the MIT License.

## Future Enhancements

- Database persistence (JPA/Hibernate)
- Authentication and authorization
- Event registration system
- Venue availability management
- Email notifications
- File upload for event images
