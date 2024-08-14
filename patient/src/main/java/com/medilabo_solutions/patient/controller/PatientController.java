package com.medilabo_solutions.patient.controller;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/patients")
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

    @GetMapping("/search")
    public Patient getPatientByName(@RequestParam String firstName, @RequestParam String lastName) {
        return iPatientService.getPatientByName(firstName, lastName);
    }

    @GetMapping("/{id}")
    public Patient getPatientById (@PathVariable Long id) {
        return iPatientService.getPatientById(id);
    }

    @PutMapping("/{firstName}/{lastName}")
    public Patient updatePatient(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Patient patient) {
        return iPatientService.updatePatient(firstName, lastName, patient);
    }



}
