# Eventify API
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com)
[![Coverage](https://img.shields.io/badge/coverage-95%25-brightgreen)](https://github)
[![Java](https://img.shields.io/badge/java-21-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-2.7.18-green)](https://spring.io/projects/spring-boot)

A Spring Boot application for managing events and venues. This project implements a clean architecture following Spring MVC patterns with in-memory data storage.

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

- Database persistence (JPA/Hibernate)
- Authentication and authorization
- Event registration system
- Venue availability management
- Email notifications
- File upload for event images
