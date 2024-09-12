# Diabetes Microservice

## Description
The Diabetes microservice assesses a patient's risk of diabetes based on their medical history and data collected from the Notes microservice. The risk evaluation is based on predefined triggers found in the patient's notes and other patient information.

## Key Features
- Evaluate a patient's diabetes risk using medical history and notes.
- Identify risk levels such as "None", "Borderline", "In Danger", and "Early Onset" based on predefined triggers and patient demographics.
- Integrate with external services to fetch patient and note information.


## Endpoints
- `GET /diabetes/{id}`: Evaluates and retrieves the diabetes risk level for a patient with the specified ID.

## Service Logic
The service uses the following logic to determine the diabetes risk:
1. **Triggers**:
    - A list of keywords (e.g., "hemoglobine", "cholesterol") is used to identify potential risk factors in the patient's notes.
    - The `NoteServiceProxy` is used to fetch all notes associated with the patient. Each note is checked for the presence of these keywords.

2. **Patient Information**:
    - The patient’s age and gender, retrieved via the `PatientServiceProxy`, are considered to adjust risk level calculations.
    - Age and gender influence the risk assessment based on predefined criteria.

3. **Risk Levels**:
    - **None**: No significant risk factors detected.
    - **Borderline**: Some risk factors detected but not severe.
    - **In Danger**: Significant risk factors detected, indicating potential for diabetes.
    - **Early Onset**: High risk detected, indicating immediate concern.


## Endpoints Details
- `GET /diabetes/{id}`:
    - **Description**: Retrieves the diabetes risk level for a patient with the specified ID.
    - **Parameters**:
        - `id`: The ID of the patient whose risk level is to be evaluated.
    - **Response**: Returns a string representing the diabetes risk level.

## Requirements
- Java 17+
- Spring Boot 3.3.1

## Running the Application
```bash
mvn spring-boot:run
