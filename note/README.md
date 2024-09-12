# Notes Microservice

## Description
The Notes microservice manages medical notes associated with patients. It allows healthcare professionals to add and retrieve notes for patients. This service is exposed via a REST API and interacts with a database to store and manage notes.

## Key Features
- Add a new note for a patient.
- Retrieve all notes associated with a patient.
- Delete notes by patient ID.

## Endpoints
- `POST /notes/{patId}`: Adds a new note for the patient with the specified ID.
    - **Parameters**:
        - `patId`: The ID of the patient.
    - **Request Body**: A string representing the note to be added.
- `GET /notes/{patId}`: Retrieves all notes associated with the patient with the specified ID.
    - **Parameters**:
        - `patId`: The ID of the patient.
- `DELETE /notes/{patId}`: Deletes all notes associated with the patient with the specified ID.
    - **Parameters**:
        - `patId`: The ID of the patient.

## Service Logic
- **Add Note**: Creates a new note associated with the given patient ID and saves it to the database.
- **Get Notes By Patient ID**: Retrieves all notes for a patient based on their ID.
- **Delete Notes By Patient ID**: Deletes all notes associated with a patient based on their ID.

## Requirements
- Java 17+
- Spring Boot 3.3.1
- Database (MongoDB)

## Running the Application
```bash
mvn spring-boot:run
