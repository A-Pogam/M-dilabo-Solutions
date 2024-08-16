package com.medilabo_solutions.note.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "note")
public class Note {

    @Id
    private String id;
    private Long patId;
    private String patientName;
    private List<String> notes;

    public Note() {
    }

    public Note(Long patId, String patientName, List<String> notes) {
        this.patId = patId;
        this.patientName = patientName;
        this.notes = notes;
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", patId=" + patId +
                ", patientName='" + patientName + '\'' +
                ", notes=" + notes +
                '}';
    }
}