package com.medilabo_solutions.note.service;

import com.medilabo_solutions.note.model.Note;
import com.medilabo_solutions.note.repository.NoteRepository;
import com.medilabo_solutions.note.service.contracts.INoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {
    private static final Logger logger = LogManager.getLogger(NoteService.class);


    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note addNote(Long patId, String notes) {
        Note newNote = new Note();
        newNote.setPatId(patId);
        newNote.setNotes(notes);
        logger.info("Adding new note: {}", patId);
        return noteRepository.save(newNote);
    }

    @Override
    public List<Note> getNotesByPatientId(Long patId) {
        return noteRepository.findByPatId(patId);
    }
}
