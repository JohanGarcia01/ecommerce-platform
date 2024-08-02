# E-Commerce Platform

## Overview

The E-Commerce Platform is a Spring Boot web application designed for managing a simple e-commerce system. It integrates with PostgreSQL for data storage and is containerized using Docker. This application allows users to perform CRUD (Create, Read, Update, Delete) operations on e-commerce data and is documented using OpenAPI.

## Features

- **CRUD Operations**: Manage products, users, and orders through REST APIs.
- **PostgreSQL Integration**: Persistent storage of e-commerce data.
- **Docker Support**: Containerized deployment for consistent environments.
- **OpenAPI Documentation**: Swagger-based API documentation for easy integration.
- **Error Handling**: Custom error handling using `EcommerceException`-like class.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 21**
- **Gradle**
- **Docker**
- **PostgreSQL**

### Build the Project

To build the project, use Gradle. Open a terminal in the project root directory and run:

```sh
    ./gradlew build
```
This command will compile the code and package the application.

### Run the Application

You can run the application using Docker.

#### Using Docker

1. **Build Docker Image**

   Build the Docker image with:

```sh
   docker build -t e-commerce-platform .
```

2. **Run Docker Compose**

   Start the application and PostgreSQL database with Docker Compose:
```sh
   docker-compose up
```

   This will launch the application and database in separate containers.

### API Documentation

The application provides API documentation via OpenAPI. After starting the application, you can view the API documentation at:

    http://localhost:8080/swagger-ui/index.html

### Code Style

The project uses Spotless for code formatting. To apply the code formatting rules, run:

    ./gradlew spotlessApply


### Test the Application 
Along with this project you will find a postman collection that will allow you to test each of the endpoints