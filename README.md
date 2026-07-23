# Merchant Onboarding System

A Spring Boot REST API project that simulates a Merchant Onboarding system for digital payment platforms.

This project demonstrates enterprise backend development practices using Java 21, Spring Boot, Spring Data JPA, REST APIs, DTOs, validation, exception handling, and Swagger.

---

## Features

- Merchant Registration
- Get Merchant by ID
- Get All Merchants
- Update Merchant Details
- Delete Merchant
- Input Validation
- Global Exception Handling
- Standard API Response
- Swagger/OpenAPI Documentation
- H2 Database Integration

---

## Technology Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- Maven
- H2 Database
- Swagger / OpenAPI
- Git & GitHub

---

## Project Structure

```
backend
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
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

---

## API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## How to Run

Clone Repository

```
git clone https://github.com/ersushant/merchant-onboarding-demo.git
```

Go inside project

```
cd merchant-onboarding-demo/backend
```

Run

```
mvn spring-boot:run
```

---

## Future Enhancements

- MySQL Database
- Docker
- JWT Authentication
- Role Based Authorization
- Unit Testing
- React Frontend
- Deployment on AWS / Render

---

## Author

**Sushant Priyadershi**

GitHub:
https://github.com/ersushant
