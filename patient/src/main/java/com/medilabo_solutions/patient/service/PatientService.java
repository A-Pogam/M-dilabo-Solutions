package com.medilabo_solutions.patient.service;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.repository.contracts.PatientRepository;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService implements IPatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public Patient addPatient(Patient patient) {
        logger.info("Adding new patient: {}", patient);
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(String firstName, String lastName, Patient updatedPatient) {
        Patient patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setDateOfBirth(updatedPatient.getDateOfBirth());
        patient.setGender(updatedPatient.getGender());
        patient.setPostalAddress(updatedPatient.getPostalAddress());
        patient.setPhoneNumber(updatedPatient.getPhoneNumber());
        logger.info("Updating patient with name {} {}: {}", firstName, lastName, updatedPatient);
        return patientRepository.save(patient);
    }

    public Patient getPatientByName(String firstName, String lastName) {
        logger.info("Fetching patient with name {} {}", firstName, lastName);
        return patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        return patientRepository.findAll();
    }
}
