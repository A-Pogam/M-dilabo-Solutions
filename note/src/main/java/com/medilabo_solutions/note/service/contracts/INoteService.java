package com.medilabo_solutions.note.service.contracts;

import com.medilabo_solutions.note.model.Note;

import java.util.List;

public interface INoteService {

    String addNote(Long patId, String noteContent);
    List<Note> getNotesByPatientId(Long patId);
    void deleteNoteByPatientId(Long patId);

}
