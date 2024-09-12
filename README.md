# Médilabo Solutions
## Overview

**Médilabo** is a microservices-based healthcare application designed to manage patients, medical notes, and assess diabetes risks. The system is composed of four microservices:

1. **Patient Service**: Manages patient records and details.
2. **Notes Service**: Handles medical notes related to patient history.
3. **Diabetes Service**: Evaluates diabetes risk levels based on patient information and medical notes.
4. **API Gateway**: Acts as a central gateway for routing requests to the relevant microservices, with security management.

## Architecture

The application is built using a microservices architecture where each service is independently deployed and communicates with others via RESTful APIs. The communication between services is facilitated using **Feign clients**, and **Spring Cloud Gateway** is used for routing and security.

### Microservices:

1. **Patient Service** (`patient-service`):
    - Manages patient records, including creation, update, retrieval, and deletion.
    - Endpoints for patient CRUD operations.

2. **Notes Service** (`notes-service`):
    - Stores and retrieves patient medical notes.
    - Allows adding, fetching, and deleting notes based on patient ID.

3. **Diabetes Service** (`diabetes-service`):
    - Calculates the risk of diabetes for a patient by analyzing their medical history (notes) and personal information.
    - Integrates with the **Patient** and **Notes** services using Feign proxies.

4. **API Gateway** (`gateway-service`):
    - Centralized entry point for routing requests to microservices.
    - Implements security (authentication and authorization) using Spring Security and Spring WebFlux.

## Technologies Used

- **Spring Boot** (Microservices Framework)
- **Spring Cloud OpenFeign** (Inter-service communication)
- **Spring WebFlux** (Reactive programming model)
- **Spring Security** (For securing the APIs)
- **Spring Cloud Gateway** (API gateway for routing and security)
- **Maven** (Build tool)
- **Log4j** (Logging)

## Service Details

### 1. **Patient Service**
- **Port**: `8082`
- **Description**: Handles patient information such as name, gender, birthdate, and contact details.
- **Endpoints**:
    - `GET /patients`: Retrieve all patients.
    - `GET /patients/{id}`: Get details for a specific patient.
    - `POST /patients`: Add a new patient.
    - `PUT /patients/{id}`: Update existing patient details.
    - `DELETE /patients/{id}`: Remove a patient.

### 2. **Notes Service**
- **Port**: `8083`
- **Description**: Stores and manages medical notes related to patients.
- **Endpoints**:
    - `GET /notes/{patId}`: Get notes for a specific patient.
    - `POST /notes/{patId}`: Add a new note for a patient.
    - `DELETE /notes/{patId}`: Delete notes for a specific patient.

### 3. **Diabetes Service**
- **Port**: `8084`
- **Description**: Evaluates diabetes risk based on the patient's medical history and personal information.
- **Endpoints**:
    - `GET /diabetes/{id}`: Get the diabetes risk level for a patient by their ID.

- **Service Logic**:
    - The service uses predefined **triggers** (keywords like "hemoglobine", "cholesterol") to analyze patient notes.
    - It integrates with the **Patient Service** and **Notes Service** via **Feign clients** to fetch relevant data.
    - Risk levels are calculated based on the presence of these triggers and the patient’s age and gender.

### 4. **API Gateway**
- **Port**: `8080`
- **Description**: Central gateway for routing requests to the microservices and securing endpoints.
- **Security**:
    - Configured using **Spring Security**.
    - Routes:
        - `/patients`: Accessible to users with the roles `USER` or `ADMIN`.
        - `/notes` and `/diabetes`: Restricted to users with the `ADMIN` role.
    - Provides **HTTP Basic Authentication**.

## Running the Application
### Steps to Run:
1. Clone the repository:
   ```bash
   git clone https://github.com/A-Pogam/M-dilabo-Solutions

2. Build the application:
    ```bash
    mvn clean install

3. Run each microservice:
    ```bash
    mvn spring-boot:run -pl ServiceDiscoveryApplication
    mvn spring-boot:run -pl patientApplication
    mvn spring-boot:run -pl notesApplication
    mvn spring-boot:run -pl diabetesApplication
    mvn spring-boot:run -pl gatewayApplication

## GreenCode
Green Code, also known as responsible code, is part of a larger movement called green IT.

This concept encompasses all information and communication technologies that contribute to achieving sustainable development goals. It considers the environmental, social, and economic impacts of these activities.

The main issues highlighted are:

- Excessive consumption of disk space or resources by software.
- Software often includes many unnecessary and non-essential features.

Unlike traditional coding, eco-friendly coding focuses on essentials. As a result, you get a lighter, faster, and more resource (and energy) efficient application or website.

One of the key solutions to reduce consumption for businesses is to develop leaner and better designed code known as "green code." Alongside optimizing the code, it’s also important to trim down the "digital fat" by removing all unnecessary features, and thus, the corresponding lines of code, to focus on what's truly essential.
