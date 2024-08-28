package com.medilabo_solutions.note.service;

import com.medilabo_solutions.note.model.Note;
import com.medilabo_solutions.note.repository.NoteRepository;
import com.medilabo_solutions.note.service.contracts.INoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public String addNote(Long patId, String noteContent) {
        // Récupérer les notes existantes pour le patient
        List<Note> existingNotes = noteRepository.findByPatId(patId);

        if (existingNotes != null && !existingNotes.isEmpty()) {
            // Prendre la première note trouvée pour ce patient
            Note existingNote = existingNotes.get(0);

            // Ajouter la nouvelle note à la liste des notes existantes
            List<String> updatedNotes = existingNote.getNotes();
            updatedNotes.add(noteContent); // Ajouter la nouvelle note
            existingNote.setNotes(updatedNotes);

            // Mettre à jour la fiche existante
            noteRepository.save(existingNote);
        } else {
            // Créer une nouvelle note si aucune note n'existe pour ce patient
            List<String> notesList = new ArrayList<>();
            notesList.add(noteContent);
            Note newNote = new Note();
            newNote.setPatId(patId);
            newNote.setPatientName(""); // Définir le nom si nécessaire
            newNote.setNotes(notesList);
            noteRepository.save(newNote);
        }

        logger.info("Added new note for patient ID: {}", patId);
        return noteContent; // Retourner uniquement le contenu de la nouvelle note
    }

    @Override
    public List<Note> getNotesByPatientId(Long patId) {
        return noteRepository.findByPatId(patId);
    }

    @Override
    public void deleteNoteByPatientId(Long patId) {
        noteRepository.deleteByPatId(patId);
    }
}
