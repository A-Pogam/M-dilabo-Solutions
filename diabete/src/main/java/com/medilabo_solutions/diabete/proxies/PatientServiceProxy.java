package com.medilabo_solutions.diabete.proxies;

import com.medilabo_solutions.diabete.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", url = "http://localhost:8082/patients")
public interface PatientServiceProxy {
    @GetMapping("/{id}")
    Patient getPatientById(@PathVariable Long id);
}
