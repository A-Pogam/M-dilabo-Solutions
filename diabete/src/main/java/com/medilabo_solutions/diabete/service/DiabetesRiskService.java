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

    // Ajouter les versions avec et sans accents
    private final List<String> triggers = Arrays.asList("hemoglobine A1C", "microalbumine", "taille", "tailles",
            "poids", "fumeur", "fumeuse", "anormal", "anormaux", "anormales", "anormale",
            "cholestérol", "vertige", "rechute",
            "réaction", "anticorps");

    @Autowired
    public DiabetesRiskService(PatientServiceProxy patientServiceClient, NoteServiceProxy noteServiceClient) {
        this.patientServiceClient = patientServiceClient;
        this.noteServiceClient = noteServiceClient;
    }

    @Override
    public String evaluateDiabetesRisk(Long id) {
        // Récupérer les informations du patient
        Patient patient = patientServiceClient.getPatientById(id);

        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        // Récupérer les notes du patient
        Note[] notes = noteServiceClient.getNotesByPatientId(id);

        if (notes == null) {
            throw new RuntimeException("Notes not found for patient");
        }

        // Compter le nombre de déclencheurs dans les notes
        long triggerCount = Arrays.stream(notes)
                .map(Note::getNotes)
                .flatMap(note -> Arrays.stream(note.split("\\P{L}+")))
                .map(this::normalizeString)
                .filter(triggers::contains)
                .count();

        return determineRiskLevel(patient, triggerCount);
    }

    private String normalizeString(String input) {
        // Convertir en minuscules
        String lowerCased = input.toLowerCase(Locale.ROOT);
        // Supprimer les diacritiques (accents)
        String normalized = Normalizer.normalize(lowerCased, Normalizer.Form.NFD);
        // Supprimer les caractères non alphabétiques
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    private String determineRiskLevel(Patient patient, long triggerCount) {
        int age = calculateAge(patient.getDateOfBirth());
        String gender = patient.getGender().toLowerCase(Locale.ROOT);

        if (triggerCount == 0) {
            return "None";
        } else if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
            return "Borderline";
        } else if (age < 30) {
            if ("male".equals(gender) && triggerCount >= 3 && triggerCount < 5) {
                return "In Danger";
            } else if ("female".equals(gender) && triggerCount >= 4 && triggerCount < 7) {
                return "In Danger";
            }
        } else if (age > 30) {
            if (triggerCount >= 6 && triggerCount < 8) {
                return "In Danger";
            } else if (triggerCount >= 8) {
                return "Early Onset";
            }
        } else if (age < 30) {
            if ("male".equals(gender) && triggerCount >= 5) {
                return "Early Onset";
            } else if ("female".equals(gender) && triggerCount >= 7) {
                return "Early Onset";
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
