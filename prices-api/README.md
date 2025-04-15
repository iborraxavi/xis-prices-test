# Prices API

**Prices API** is an API designed to handle pricing information. This project provides a reactive Spring Boot 3-based implementation and adheres to OpenAPI standards.

## Features
- Reactive implementation using Spring WebFlux.
- Bean validation enabled for request validation.
- OpenAPI-based code generation for controllers and models.
- Compatible with Java 21.

## Requirements
- Java 21
- Maven 3.8+
- Spring Boot 3.3.4

## Technologies Used
- **Java 21**: Latest Java features.
- **Spring Boot**: Bootstrapping framework for microservices.
    - `spring-boot-starter-webflux`: For reactive programming.
    - `spring-boot-starter-validation`: For input validation.
- **OpenAPI Generator**: To auto-generate API controllers and models.
- **Swagger Annotations**: Simplifies Swagger documentation.
- **Jackson Databind Nullable**: Improves JSON serialization/deserialization handling for nullable types.
- **Validation API**: Provides a robust validation framework.

## Installation & Setup

1. **Clone the repository**:

   ```bash
   git clone https://github.com/iborraxavi/xis-prices-test.git
   cd prices-api
   ```

2. **Build the project**:
   Ensure Maven is installed on your machine.

   ```bash
   mvn clean install
   ```

## Project Structure
- **API Controllers**: Generated with OpenAPI Generator — located in `com.xis.prices.pricesapi.controller`.
- **Data Models**: DTOs reside in `com.xis.prices.pricesapi.dto`.

## OpenAPI Integration
This project uses `openapi-generator-maven-plugin` for generating code based on the API specification located at: src/main/resources/api/openapi.yaml

You can update the API specification and regenerate the controllers and models by running:

```bash
  mvn openapi-generator:generate
```

## License
This project is for testing purposes. Feel free to adapt or enhance it as needed.

---

