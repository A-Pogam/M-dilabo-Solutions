
---

# Gateway Microservice

## Description
The Gateway microservice acts as a single entry point to the system’s various services (patients, notes, diabetes). It routes requests to the appropriate services and implements security and filtering features.

## Key Features
- Dynamic routing to the patient, notes, and diabetes microservices.
- Centralized authentication and authorization with Spring Security.
- Role-based access control (user/admin) to restrict access.

## Endpoints
The Gateway exposes routes to the following services:
- `/patients/**`: Access to the Patients service.
- `/notes/**`: Access to the Notes service.
- `/diabetes/**`: Access to the Diabetes service.
- 
## Security Configuration
The security configuration is managed using Spring WebFlux Security and includes:

- **User Details**:
    - **User**:
        - Username: `user`
        - Password: `pass`
        - Role: `USER`
    - **Admin**:
        - Username: `admin`
        - Password: `adminpass`
        - Role: `ADMIN`
- **Access Control**:
    - **/patients**: Accessible to users with the `USER` or `ADMIN` role.
    - **/notes**, **/diabetes**: Restricted to users with the `ADMIN` role.
    - All other requests require authentication.

## Requirements
- Java 17+
- Spring Boot 3.3.1
- Spring Cloud Gateway

## Running the Application
```bash
mvn spring-boot:run
