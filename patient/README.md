# Patients Microservice

## Description
The Patients microservice manages patient information. It allows for creating, reading, updating, and deleting patient records. This service is exposed via a REST API and interacts with a database to store and manage patient data.

## Key Features
- Create a new patient.
- Retrieve patient information by ID or name.
- Update patient information.
- Retrieve all patients.

## Endpoints
- `GET /patients`: Retrieves a list of all patients.
- `GET /patients/search`: Retrieves a patient by first name and last name.
    - **Parameters**:
        - `firstName`: The first name of the patient.
        - `lastName`: The last name of the patient.
- `GET /patients/{id}`: Retrieves a patient by their ID.
- `PUT /patients/{id}`: Updates an existing patient's information.
    - **Parameters**:
        - `id`: The ID of the patient to update.
    - **Request Body**: JSON object representing the updated patient information.

## Service Logic
- **Add Patient**: Creates a new patient record and saves it to the database.
- **Update Patient**: Updates the patient record with the specified ID using provided information.
- **Get Patient By Name**: Fetches a patient record based on first and last name.
- **Get Patient By ID**: Fetches a patient record based on the patient's ID.
- **Get All Patients**: Retrieves all patient records.

## Requirements
- Java 17+
- Spring Boot 3.3.1
- Database (MySQL)

## Running the Application
```bash
mvn spring-boot:run
