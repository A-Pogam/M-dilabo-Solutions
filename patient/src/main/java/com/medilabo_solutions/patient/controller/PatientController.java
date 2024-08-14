package com.medilabo_solutions.patient.controller;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Patient updatedPatient = iPatientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }


}
