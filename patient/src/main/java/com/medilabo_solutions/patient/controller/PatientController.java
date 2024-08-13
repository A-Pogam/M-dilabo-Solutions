package com.medilabo_solutions.patient.controller;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //More accurate for React JSON (instead of Controlller HTML)
@RequestMapping("/patient")
public class PatientController {

    private IPatientService iPatientService;

    @Autowired
    public PatientController(IPatientService iPatientService) {
        this.iPatientService = iPatientService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return iPatientService.getAllPatients();
    }

    @GetMapping("/patients/{firstName}/{lastName}")
    public Patient getPatientByName(@PathVariable String firstName, @PathVariable String lastName) {
        return iPatientService.getPatientByName(firstName, lastName);
    }

    @PutMapping("/patients/{firstName}/{lastName}")
    public Patient updatePatient(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Patient patient) {
        return iPatientService.updatePatient(firstName, lastName, patient);
    }
}
