# Merchant Onboarding System

A Spring Boot REST API project that simulates a Merchant Onboarding system for digital payment platforms.

The project demonstrates enterprise backend development practices using Java 21, Spring Boot, Spring Data JPA, REST APIs, layered architecture, DTOs, validation, centralized exception handling, Swagger/OpenAPI, and Spring Security.

---

## Project Status

### Completed

- Merchant CRUD APIs

- Layered Architecture

- DTO Pattern

- Request Validation

- Global Exception Handling

- Standard API Response

- Swagger/OpenAPI Documentation

- Spring Data JPA

- H2 In-Memory Database

- Logging

- Unit Testing

- Spring Security Configuration

### Work In Progress

- JWT Authentication

User Registration has been successfully implemented.

JWT-based Login Authentication has also been implemented, but is currently under debugging. The existing implementation has been intentionally kept in the repository to demonstrate the authentication approach and ongoing development.

---

## Features

- Merchant Registration

- Merchant CRUD Operations

- Request & Response DTOs

- Validation using Jakarta Validation

- Global Exception Handling

- Standard API Response

- Swagger Documentation

- H2 Database

- Spring Security

- Logging

- Unit Testing

---

## Technology Stack

- Java 21

- Spring Boot 3

- Spring Data JPA

- Spring Security

- Maven

- H2 Database

- Swagger / OpenAPI

- Lombok

- Git & GitHub

---

## Why H2 Database?

This project currently uses the **H2 In-Memory Database** instead of MySQL.

Reasons:

- No external database installation is required.

- Easy for reviewers and recruiters to run the project.

- Faster development and testing.

- Database schema is created automatically at application startup.

- The application is designed so that migrating to MySQL/PostgreSQL only requires datasource configuration changes.

---

## Project Structure

```

backend

 ├── controller

 ├── service

 │    └── impl

 ├── repository

 ├── entity

 ├── dto

 ├── security

 ├── config

 ├── response

 ├── exception

 └── resources

```

---

## REST APIs

| Method | Endpoint | Description |

|--------|----------|-------------|

| POST | /api/merchants | Create Merchant |

| GET | /api/merchants | Get All Merchants |

| GET | /api/merchants/{id} | Get Merchant By Id |

| PUT | /api/merchants/{id} | Update Merchant |

| DELETE | /api/merchants/{id} | Delete Merchant |

| POST | /api/auth/register | User Registration |

| POST | /api/auth/login | JWT Login (Currently Under Debugging) |

---

## API Documentation

Swagger UI

```

http://localhost:8080/swagger-ui/index.html

```

---

## How to Run

Clone Repository

```bash

git clone https://github.com/ersushant/merchant-onboarding-demo.git

```

Navigate into the project

```bash

cd merchant-onboarding-demo/backend

```

Run the application

```bash

mvn spring-boot:run

```

Swagger UI

```

http://localhost:8080/swagger-ui/index.html

```

---

## Future Enhancements

- Complete JWT Authentication

- Role Based Authorization

- MySQL Integration

- Docker Support

- Redis Caching

- CI/CD using GitHub Actions

- React Frontend

- AWS Deployment

---

## Author

**Sushant Priyadershi**

GitHub

https://github.com/ersushant/merchant-onboarding-demo