package com.medilabo_solutions.note.service;

import com.medilabo_solutions.note.model.Note;
import com.medilabo_solutions.note.repository.NoteRepository;
import com.medilabo_solutions.note.service.contracts.INoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoteServiceIT {

    @Autowired
    private INoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setup() {
        noteRepository.deleteAll();
    }

    @Test
    public void addNote_shouldSaveNote() {
        Long patientId = 1L;
        String noteContent = "Test note";

        Note savedNote = noteService.addNote(patientId, noteContent);

        assertEquals(patientId, savedNote.getPatId());
        assertEquals(noteContent, savedNote.getNotes());

        List<Note> notes = noteRepository.findByPatId(patientId);
        assertEquals(1, notes.size());
        assertEquals(noteContent, notes.get(0).getNotes());
    }

    @Test
    public void getNotesByPatientId_shouldReturnListOfNotes() {
        Long patientId = 1L;

        noteService.addNote(patientId, "Note 1");
        noteService.addNote(patientId, "Note 2");

        List<Note> notes = noteService.getNotesByPatientId(patientId);

        assertEquals(2, notes.size());
        assertEquals("Note 1", notes.get(0).getNotes());
        assertEquals("Note 2", notes.get(1).getNotes());
    }
}
