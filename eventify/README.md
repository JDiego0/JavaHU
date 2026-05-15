# Eventify API
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com)
[![Coverage](https://img.shields.io/badge/coverage-95%25-brightgreen)](https://github.com)
[![Java](https://img.shields.io/badge/java-21-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-2.7.18-green)](https://spring.io/projects/spring-boot)

A Spring Boot application for managing events and venues. This project implements a clean architecture following Spring MVC patterns with **persistent database storage** using Spring Data JPA and Hibernate.

## 🚀 Quick Start (5 minutos)

1. **Iniciar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

2. **Abrir Swagger UI**:
   Visita http://localhost:8080/swagger-ui.html

3. **Probar la API**:
   Usa la interfaz interactiva de Swagger para crear y consultar eventos y lugares

---

## Features

- **Event Management**: Create, read, update, and delete events with full CRUD operations
- **Venue Management**: Create, read, update, and delete venues with full CRUD operations
- **Persistent Storage**: H2 database with Spring Data JPA and Hibernate for data persistence
- **Pagination & Sorting**: Scalable list endpoints with page, size, and sort parameters
- **RESTful API**: Full REST endpoints with proper HTTP status codes (200, 201, 204, 400, 404)
- **Swagger Documentation**: Interactive API documentation with pagination and error code details
- **Integration Testing**: @DataJpaTest for repository layer validation
- **Unit Testing**: Comprehensive test coverage with JUnit 5 and Mockito
- **Validation**: Business logic validation for all entities with proper error handling

## Technologies Used

- **Java 21**
- **Spring Boot 2.7.18**
- **Spring Web MVC**
- **Spring Data JPA** - For database persistence
- **Hibernate** - ORM framework
- **H2 Database** - In-memory/file-based database for development
- **Lombok** - For reducing boilerplate code
- **SpringDoc OpenAPI** - For Swagger documentation
- **JUnit 5** - For unit and integration testing
- **Mockito** - For mocking in tests
- **AssertJ** - For fluent assertions in tests

## Project Structure

```
src/
├── main/
│   └── java/com/riwi/eventify/
│       ├── controllers/     # REST Controllers with pagination
│       ├── services/        # Business Logic Layer
│       ├── repositories/    # JPA Repository interfaces
│       ├── models/          # JPA Entity Classes
│       ├── config/          # Configuration Classes (Swagger, Seeder)
│       └── EventifyApplication.java
└── test/
    └── java/com/riwi/eventify/
        ├── repositories/    # @DataJpaTest integration tests
        └── services/        # Service Layer Tests
```

## API Endpoints

### Events
- `GET /api/events` - Get all events (supports pagination: `?page=0&size=10&sort=name,asc`)
- `POST /api/events` - Create a new event
- `GET /api/events/{id}` - Get event by ID
- `PUT /api/events/{id}` - Update an existing event
- `DELETE /api/events/{id}` - Delete event by ID

### Venues
- `GET /api/venues` - Get all venues (supports pagination: `?page=0&size=10&sort=name,asc`)
- `POST /api/venues` - Create a new venue
- `GET /api/venues/{id}` - Get venue by ID
- `PUT /api/venues/{id}` - Update an existing venue
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

## 📚 API Documentation

**🎯 OBJETIVO PRINCIPAL**: La documentación interactiva Swagger es el punto central de este proyecto.

### Acceso a Swagger UI

Una vez que la aplicación está corriendo, puedes acceder a la documentación interactiva en:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### 📋 ¿Qué encontrarás en Swagger?

- **Endpoints documentados**: Todos los GET y POST para eventos y lugares
- **Interfaz interactiva**: Prueba la API directamente desde tu navegador
- **Ejemplos de uso**: JSON pre-configurados para facilitar las pruebas
- **Validaciones**: Documentación de errores y códigos de estado

### 🔥 Características destacadas de Swagger
- **Try it out**: Ejecuta endpoints sin necesidad de herramientas externas
- **Auto-generación**: Documentación siempre sincronizada con el código
- **Exportación**: Descarga especificaciones OpenAPI para integración

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

### Get All Events with Pagination
```bash
curl -X GET "http://localhost:8080/api/events?page=0&size=5&sort=name,asc"
```

### Get All Venues
```bash
curl -X GET http://localhost:8080/api/venues
```

### Get All Venues with Pagination
```bash
curl -X GET "http://localhost:8080/api/venues?page=0&size=5&sort=name,asc"
```

### Update an Event
```bash
curl -X PUT http://localhost:8080/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Rock Concert",
    "description": "Updated description",
    "date": "2024-06-15T20:00:00",
    "venue": "National Stadium"
  }'
```

### Update a Venue
```bash
curl -X PUT http://localhost:8080/api/venues/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated National Stadium",
    "address": "456 Updated Street",
    "capacity": 60000
  }'
```

### Delete an Event
```bash
curl -X DELETE http://localhost:8080/api/events/1
```

### Delete a Venue
```bash
curl -X DELETE http://localhost:8080/api/venues/1
```

## Configuration

### Database Configuration

The application uses H2 database with file-based persistence. Database configuration in `application.properties`:

```properties
# H2 Database
spring.datasource.url=jdbc:h2:file:./data/eventify
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (for development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### H2 Console Access

Access the H2 database console at: http://localhost:8080/h2-console

**Connection Details:**
- JDBC URL: `jdbc:h2:file:./data/eventify`
- User Name: `sa`
- Password: (leave empty)

### Data Seeding

The application includes optional data seeding. To enable it, add the following to your `application.properties`:

```properties
app.seed.enabled=true
```

When enabled, the application will pre-populate with sample events and venues on startup.

## Architecture

This project follows a clean layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses with pagination support
2. **Service Layer**: Contains business logic and validation
3. **Repository Layer**: JPA repositories for database operations with derived queries
4. **Model Layer**: JPA entity classes with proper annotations and constraints

All layers use constructor dependency injection for better testability and maintainability.

### JPA Entity Mapping

- **@Entity**: Marks classes as JPA entities
- **@Table**: Specifies database table names
- **@Id**: Defines primary keys
- **@GeneratedValue**: Auto-generates IDs using IDENTITY strategy
- **@Column**: Defines column constraints (nullable, length)

### Derived Queries

Repositories include custom derived queries:
- `findByNameContainingIgnoreCase`: Case-insensitive name search
- `findByVenue`: Custom @Query for venue-based event search
- `findByAddressContainingIgnoreCase`: Case-insensitive address search

## Testing

The project includes comprehensive tests for both service and repository layers:

### Service Layer Tests
- **EventServiceTest**: 9 test cases covering all event operations
- **VenueServiceTest**: 12 test cases covering all venue operations

Tests use Mockito for mocking dependencies and follow the AAA pattern (Arrange, Act, Assert).

### Repository Integration Tests
- **EventRepositoryTest**: @DataJpaTest with 9 test cases
  - Entity persistence validation
  - CRUD operations testing
  - Pagination and sorting verification
  - Derived query testing (findByNameContainingIgnoreCase, findByVenue)
  - Existence checks

- **VenueRepositoryTest**: @DataJpaTest with 9 test cases
  - Entity persistence validation
  - CRUD operations testing
  - Pagination and sorting verification
  - Derived query testing (findByNameContainingIgnoreCase, findByAddressContainingIgnoreCase)
  - Existence checks

### Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=EventServiceTest
mvn test -Dtest=VenueServiceTest
mvn test -Dtest=EventRepositoryTest
mvn test -Dtest=VenueRepositoryTest
```

## Error Handling

The API returns appropriate HTTP status codes:
- `201 Created`: Successful resource creation
- `200 OK`: Successful read operations
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Resource not found (non-existent ID)
- `204 No Content`: Successful deletion

### 404 Not Found Handling

When attempting to access, update, or delete a non-existent resource (e.g., ID: 9999), the system returns:
- HTTP Status: `404 Not Found`
- Clear error message indicating the resource was not found

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run all tests to ensure they pass
6. Submit a pull request

## License

This project is licensed under the MIT License.

## 🛠️ Troubleshooting

### Problemas comunes y soluciones

#### 🚫 Puerto 8080 ocupado
**Error**: `Port 8080 is already in use`
**Solución**: Cambia el puerto en `src/main/resources/application.properties`:
```properties
server.port=8081
```

#### ☕ Versión de Java incorrecta
**Error**: `Java version mismatch`
**Solución**: Asegúrate de tener Java 21 instalado:
```bash
java -version # Debe mostrar Java 21
```

#### 📦 Dependencias no encontradas
**Error**: `Could not resolve dependencies`
**Solución**: Limpia y reinstala dependencias:
```bash
mvn clean install
```

#### 🔍 Swagger no accesible
**Error**: `404 Not Found` al acceder a Swagger
**Solución**: Verifica que la aplicación esté corriendo correctamente:
```bash
mvn spring-boot:run
# Espera el mensaje "Started EventifyApplication"
```

### 📞 Ayuda adicional
- Revisa los logs de la aplicación para errores detallados
- Asegúrate de cumplir todos los prerrequisitos
- Consulta la sección de pruebas para verificar el funcionamiento

---

## 🚀 Future Enhancements

- Authentication and authorization (Spring Security)
- Event registration system
- Venue availability management
- Email notifications
- File upload for event images
- Relationship between Events and Venues (foreign keys)
- Advanced filtering and search capabilities
- Export data to CSV/Excel

---

## ✅ Acceptance Criteria Met

### Escenario 1: Persistencia Post-Reinicio (Camino Feliz)
✅ **Implementado**: Los datos se guardan en base de datos H2 persistente (archivo). Al reiniciar la aplicación, los datos siguen disponibles.

### Escenario 2: Intento de Acceso a Recurso Inexistente (Camino de Error)
✅ **Implementado**: Al consultar, actualizar o eliminar un ID inexistente (ej. ID: 9999), el sistema retorna `404 Not Found` con mensaje claro.

### Escenario 3: Paginación de Resultados (Caso de Volumen)
✅ **Implementado**: Los endpoints de listado soportan parámetros `page`, `size`, y `sort`. Ejemplo: `?page=0&size=5&sort=name,asc` retorna exactamente 5 registros con metadatos de paginación.

### Escenario 4: Eliminación Exitosa
✅ **Implementado**: Al eliminar un recurso, el sistema retorna `204 No Content`, confirmando que el recurso ya no existe.

---

## 📊 Pagination Details

### Pagination Parameters

All list endpoints support the following query parameters:

- **page** (default: 0): Page number to retrieve
- **size** (default: 10): Number of items per page
- **sort** (default: id): Field to sort by
- **direction** (default: asc): Sort direction (asc or desc)

### Example Requests

```bash
# Get first page with 10 items, sorted by name ascending
GET /api/events?page=0&size=10&sort=name&direction=asc

# Get second page with 5 items, sorted by id descending
GET /api/venues?page=1&size=5&sort=id&direction=desc
```

### Response Format

Paginated responses include metadata:

```json
{
  "content": [...],
  "pageable": {...},
  "totalElements": 50,
  "totalPages": 10,
  "size": 5,
  "number": 0
}
```
