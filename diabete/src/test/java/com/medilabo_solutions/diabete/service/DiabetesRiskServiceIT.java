package com.medilabo_solutions.diabete.service;

import com.medilabo_solutions.diabete.model.Note;
import com.medilabo_solutions.diabete.model.Patient;
import com.medilabo_solutions.diabete.proxies.NoteServiceProxy;
import com.medilabo_solutions.diabete.proxies.PatientServiceProxy;
import com.medilabo_solutions.diabete.service.contracts.IDiabetesRiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DiabetesRiskServiceIT {

    @Autowired
    private IDiabetesRiskService diabetesRiskService;

    @MockBean
    private PatientServiceProxy patientServiceProxy;

    @MockBean
    private NoteServiceProxy noteServiceProxy;

    @Test
    public void testEvaluateDiabetesRisk_withRealService() {
        Long patientId = 1L;

        Patient mockPatient = new Patient();
        mockPatient.setId(patientId);
        mockPatient.setDateOfBirth(new Date(70, 0, 1));
        mockPatient.setGender("male");

        Note note1 = new Note();
        note1.setNotes("Fumeur, anormal, rechute, vertige, anormal, microalbumine, hemoglobine");

        Note[] mockNotes = { note1 };

        when(patientServiceProxy.getPatientById(patientId)).thenReturn(mockPatient);
        when(noteServiceProxy.getNotesByPatientId(patientId)).thenReturn(mockNotes);

        String riskLevel = diabetesRiskService.evaluateDiabetesRisk(patientId);

        assertEquals("In Danger", riskLevel);
    }
}
