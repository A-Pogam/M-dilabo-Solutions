package com.medilabo_solutions.diabete.model;

public class Note {

    private Long patId;


    private String notes;

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Note(Long patId, String notes) {
        this.patId = patId;
        this.notes = notes;
    }

    public Note () {

    }
}

