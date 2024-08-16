package com.medilabo_solutions.note.service.contracts;

import com.medilabo_solutions.note.model.Note;

import java.util.List;

public interface INoteService {

    Note addNote(Note note);
    List<Note> getNotesByPatientId(Long patId);
    Note getNoteById(String id);
    void deleteNoteById(String id);
    List<Note> getNotesByPatientName(String patientName);

}
