package com.medilabo_solutions.note.controller;

import com.medilabo_solutions.note.model.Note;
import com.medilabo_solutions.note.service.contracts.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INoteService noteService;

    @Autowired
    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/{patId}")
    public String addNote(@PathVariable Long patId, @RequestBody String noteContent) {
        return noteService.addNote(patId, noteContent);
    }

    @GetMapping("/{patId}")
    public List<Note> getNotesByPatientId(@PathVariable Long patId) {
        return noteService.getNotesByPatientId(patId);
    }

}
