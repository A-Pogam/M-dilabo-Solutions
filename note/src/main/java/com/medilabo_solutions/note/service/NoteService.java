package com.medilabo_solutions.note.service;

import com.medilabo_solutions.note.model.Note;
import com.medilabo_solutions.note.repository.NoteRepository;
import com.medilabo_solutions.note.service.contracts.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNotesByPatientId(Long patId) {
        return noteRepository.findByPatId(patId);
    }

    @Override
    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }
}
