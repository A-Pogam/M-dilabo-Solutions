package com.medilabo_solutions.diabete.controller;

import com.medilabo_solutions.diabete.service.contracts.IDiabetesRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diabetes-risk")
public class DiabetesRiskController {

    private final IDiabetesRiskService diabetesRiskService;

    @Autowired
    public DiabetesRiskController(IDiabetesRiskService diabetesRiskService) {
        this.diabetesRiskService = diabetesRiskService;
    }

    @GetMapping("/{patId}")
    public ResponseEntity<String> getDiabetesRisk(@PathVariable Long patId) {
        String riskLevel = diabetesRiskService.evaluateDiabetesRisk(patId);
        return ResponseEntity.ok(riskLevel);
    }
}
