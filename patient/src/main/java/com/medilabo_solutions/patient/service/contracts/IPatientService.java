package com.medilabo_solutions.patient.service.contracts;

import com.medilabo_solutions.patient.model.Patient;

import java.util.List;

public interface IPatientService {
    Patient addPatient(Patient patient);
    Patient updatePatient(Long id, Patient updatedPatient);
    Patient getPatientByName(String firstName, String lastName);
    List<Patient> getAllPatients();

    Patient getPatientById(Long id);
}

