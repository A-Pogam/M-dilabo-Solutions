package com.medilabo_solutions.diabete;

import com.medilabo_solutions.diabete.service.contracts.IDiabetesRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diabetes")
public class DiabetesRiskController {

    private final IDiabetesRiskService diabetesRiskService;

    @Autowired
    public DiabetesRiskController(IDiabetesRiskService diabetesRiskService) {
        this.diabetesRiskService = diabetesRiskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getDiabetesRisk(@PathVariable Long id) {
        String riskLevel = diabetesRiskService.evaluateDiabetesRisk(id);
        return ResponseEntity.ok(riskLevel);
    }
}
