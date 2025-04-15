# Prices Projects

This repository contains two projects related to price management: **prices-api** and **prices-microservice**. Both projects are designed to work together, but it is important to follow a specific order when compiling them to ensure proper functionality.

## Repository Structure

- **prices-api**: Provides the OpenAPI specification and the necessary interfaces for service communication.
- **prices-microservice**: Implements the business logic, infrastructure, and REST API based on the specification provided by `prices-api`.

## Prerequisites

Before starting, make sure you have the following installed:

- **Java 21** or higher.
- **Maven 3.8+** for dependency management and compilation.

## Compilation Order

### 1. Compile `prices-api`

The `prices-api` project must be compiled first, as it generates the interfaces and models required by `prices-microservice`.

Steps to compile:

```bash
cd prices-api
mvn clean install
```

This will install the generated artifact in the local Maven repository, making it available for prices-microservice.

### 2. Compile `prices-microservice`

Once `prices-api` has been compiled and installed, you can proceed to compile `prices-microservice`.

Steps to compile:

```bash
cd ../prices-microservice
mvn clean install
```

This command will compile all the modules of the microservice and prepare them for execution.

## Running the Microservice

After compiling both projects, you can run the microservice from the `prices-microservice-application` module:

```bash
cd prices-microservice/prices-microservice-application
mvn spring-boot:run
```

The microservice will be available on port `8090` by default. You can modify this configuration in the `application.yaml` file.

## Additional Notes

- If you make changes to `prices-api`, make sure to recompile and reinstall it before recompiling `prices-microservice`.

## License

This project is for testing purposes. Feel free to adapt or improve it according to your needs.

---