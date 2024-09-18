package com.medilabo_solutions.note.service.contracts;

import com.medilabo_solutions.note.model.Note;

import java.util.List;

public interface INoteService {

    Note addNote(Long patId, String notes);
    List<Note> getNotesByPatientId(Long patId);

}

