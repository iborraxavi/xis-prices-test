# Prices Microservice

A Spring Boot-based microservice for managing and retrieving pricing data. This project is created as a modularized multi-module Maven structure.

## Project Structure

The project is divided into multiple modules for better maintainability and separation of concerns:

- **prices-microservice-application**: The main entry point of the application.
- **prices-microservice-api**: Handles external API interfaces and contracts.
- **prices-microservice-domain**: Contains the business logic and domain models.
- **prices-microservice-infrastructure**: Manages database, repository, and infrastructure setup.

## Features

- Built with **Spring Boot 3.3.4**.
- Reactive programming with **Spring WebFlux**.
- Database interactions using **Spring Data R2DBC** with H2.
- Object mapping using **MapStruct**.
- Annotation-driven coding support with **Lombok**.
- Unit and reactive tests with **Spring Boot Starter Test** and **Reactor Test**.

## Prerequisites

- **Java 21** or higher installed.
- Maven 3.8+ to build and manage dependencies.

## Build and Run

1. Clone the repository:
   ```bash
   git clone https://github.com/iborraxavi/xis-prices-test.git
   cd prices-microservice
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Go to the application module and run the application:
   ```bash
   cd prices-microservice-application
   mvn spring-boot:run
   ```

## Configuration

This project is configured with the following technologies:

- **H2 Database**: Used as a runtime in-memory database for local testing. Configurations can be modified in the `application.yml` file.
- **MapStruct**: Simplifies object mapping between the domain and DTO layers.
- **Lombok**: Reduces boilerplate code in Java classes.

## Testing

To execute all tests, run:

```bash
  mvn test
```

## Dependencies

Key dependencies include:

- **Spring Boot**
    - `spring-boot-starter-webflux`: Reactive web framework.
    - `spring-boot-starter-data-r2dbc`: For reactive database interactions.
- **MapStruct**: Object mapping library.
- **Lombok**: Automatic code generation like constructors, getters, setters.
- Testing:
    - `spring-boot-starter-test`
    - `reactor-test`

## Example API Usage

### Endpoint: Get Price by Product ID and Brand

Retrieve the price for a specific product and brand based on a given date.

**Request**:
```http
GET /prices?productId=35455&brandId=1&applicationDate=2023-11-07T10:00:00Z
```

**Query Parameters**:
- `productId` (integer): The ID of the product to retrieve the price for.
- `brandId` (integer): The brand ID to filter the price.
- `applicationDate` (ISO8601 datetime): The date to find the active price.

**CURL Example**:
```bash
  curl -X GET "http://localhost:8090/prices?productId=35455&brandId=1&applicationDate=2023-11-07T10:00:00Z" \
  -H "Content-Type: application/json"
```

**Sample Response**:
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 4,
  "startDate": "2023-11-01T00:00:00Z",
  "endDate": "2023-11-30T23:59:59Z",
  "price": 35.50,
  "currency": "EUR"
}
```

### Explanation of the Response

- `productId`: The ID of the product for which the price is retrieved.
- `brandId`: The brand corresponding to the price.
- `priceList`: The internal price list ID used to identify pricing tiers or campaigns.
- `startDate` and `endDate`: The period during which the price is valid.
- `price`: The actual price for the product within the given period.
- `currency`: The currency of the price (e.g., EUR, USD).

---

### Possible Error Responses

Here are some common errors and their possible causes:

1. **400 Bad Request**  
   The request contains missing or invalid parameters.  
   **Example Response**:
   ```json
   {
     "timestamp": "2023-11-07T10:00:00Z",
     "status": 400,
     "error": "Bad Request",
     "message": "Invalid or missing parameters."
   }
   ```

2. **404 Not Found**  
   The requested product or price is not found for the given criteria.  
   **Example Response**:
   ```json
   {
     "timestamp": "2023-11-07T10:00:00Z",
     "status": 404,
     "error": "Not Found",
     "message": "No price found for productId 35455 and brandId 1 at the date provided."
   }
   ```

3. **500 Internal Server Error**  
   An unexpected server error occurred.  
   **Example Response**:
   ```json
   {
     "timestamp": "2023-11-07T10:00:00Z",
     "status": 500,
     "error": "Internal Server Error",
     "message": "An unexpected error occurred. Please try again later."
   }
   ```

## License

This project is for testing purposes. Feel free to adapt or enhance it as needed.

---