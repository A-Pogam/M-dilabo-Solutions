package com.medilabo_solutions.authentication.repository.contracts;

import com.medilabo_solutions.authentication.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);

}

