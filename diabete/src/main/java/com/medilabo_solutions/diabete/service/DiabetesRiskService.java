package com.medilabo_solutions.diabete.service;

import com.medilabo_solutions.diabete.model.Note;
import com.medilabo_solutions.diabete.model.Patient;
import com.medilabo_solutions.diabete.proxies.NoteServiceProxy;
import com.medilabo_solutions.diabete.proxies.PatientServiceProxy;
import com.medilabo_solutions.diabete.service.contracts.IDiabetesRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class DiabetesRiskService implements IDiabetesRiskService {

    private final PatientServiceProxy patientServiceClient;
    private final NoteServiceProxy noteServiceClient;

    private final List<String> triggers = Arrays.asList("hemoglobine", "microalbumine", "taille", "tailles",
            "poids", "fumeur", "fumer", "fumeuse", "anormal", "anormaux", "anormales", "anormale",
            "cholesterol", "vertige", "rechute",
            "reaction", "anticorps");

    @Autowired
    public DiabetesRiskService(PatientServiceProxy patientServiceClient, NoteServiceProxy noteServiceClient) {
        this.patientServiceClient = patientServiceClient;
        this.noteServiceClient = noteServiceClient;
    }

    @Override
    public String evaluateDiabetesRisk(Long id) {

        Patient patient = patientServiceClient.getPatientById(id);

        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        Note[] notes = noteServiceClient.getNotesByPatientId(id);

        if (notes == null) {
            throw new RuntimeException("Notes not found for patient");
        }

        long allTriggersCount = 0;

        for (String trigger : triggers) {
            boolean isTriggerPresent = Arrays.stream(notes)
                    .map(Note::getNotes)
                    .flatMap(note -> Arrays.stream(note.split("\\P{L}+")))
                    .map(this::normalizeString)
                    .anyMatch(word -> {
                        boolean matches = word.equals(trigger);
                        if (matches) {
                            System.out.println("Trigger found: " + trigger);
                        }
                        return matches;
                    });

            if (isTriggerPresent) {
                allTriggersCount++;
                System.out.println("Trigger count incremented for trigger: " + trigger);
            }
        }

        System.out.println("Total triggers count: " + allTriggersCount);

        return determineRiskLevel(patient, allTriggersCount);
    }


    private String normalizeString(String input) {
        String lowerCased = input.toLowerCase(Locale.ROOT);
        String normalized = Normalizer.normalize(lowerCased, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    private String determineRiskLevel(Patient patient, long triggerCount) {
        int age = calculateAge(patient.getDateOfBirth());
        String gender = patient.getGender().toLowerCase(Locale.ROOT);

        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30) {
            if (triggerCount >= 8) {
                return "Early Onset";
            } else if (triggerCount >= 6) {
                return "In Danger";
            } else if (triggerCount >= 2 && triggerCount <= 5) {
                return "Borderline";
            }
        }
        else {
            if (gender.equals("male") && triggerCount >= 5) {
                return "Early Onset";
            } else if (gender.equals("female") && triggerCount >= 7) {
                return "Early Onset";
            } else if (triggerCount >= 3) {
                return "In Danger";
            }
        }

        return "None";
    }


    private int calculateAge(Date dateOfBirth) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(dateOfBirth);
        int birthYear = birthCalendar.get(Calendar.YEAR);

        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);

        return currentYear - birthYear;
    }
}