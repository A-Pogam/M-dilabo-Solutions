package com.medilabo_solutions.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.repository.contracts.PatientRepository;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PatientServiceIT {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient createPatient(Long id, String firstName, String lastName, Date dateOfBirth, String gender, String postalAddress, String phoneNumber) {
        return new Patient(id, firstName, lastName, dateOfBirth, gender, postalAddress, phoneNumber);
    }

    @Test
    public void addPatient_success() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date();
        Patient patient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.addPatient(patient);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void updatePatient_success() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date();
        Patient existingPatient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        Patient updatedPatient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "456 Other St", "987-654-3210");
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Act
        Patient result = patientService.updatePatient(1L, updatedPatient);

        // Assert
        assertEquals(updatedPatient, result);
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(updatedPatient);
    }

    @Test
    public void updatePatient_patientNotFound() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date(); // Utilisation de java.util.Date
        Patient updatedPatient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patientService.updatePatient(1L, updatedPatient);
        });

        assertEquals("Patient not found", thrown.getMessage());
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(0)).save(any(Patient.class));
    }

    @Test
    public void getPatientByName_success() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date();
        Patient patient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        when(patientRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientByName("John", "Doe");

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    public void getPatientByName_patientNotFound() {
        MockitoAnnotations.openMocks(this);

        when(patientRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientByName("John", "Doe");
        });

        assertEquals("Patient not found", thrown.getMessage());
        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    public void getAllPatients_success() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date();
        Patient patient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        List<Patient> patients = new ArrayList<>(Arrays.asList(patient, patient, patient));
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertEquals(patients, result);
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void getPatientById_success() {
        MockitoAnnotations.openMocks(this);

        Date dateOfBirth = new Date();
        Patient patient = createPatient(1L, "John", "Doe", dateOfBirth, "Male", "123 Main St", "123-456-7890");
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1L);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void getPatientById_patientNotFound() {
        MockitoAnnotations.openMocks(this);

        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(1L);
        });

        assertEquals("Patient not found", thrown.getMessage());
        verify(patientRepository, times(1)).findById(1L);
    }
}
