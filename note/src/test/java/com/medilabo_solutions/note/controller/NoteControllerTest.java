package com.medilabo_solutions.note.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo_solutions.note.service.contracts.INoteService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private INoteService noteService;

    @Test
    public void addNote_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/notes/{patId}", 1L)
                        .content("New medical note")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getNotesByPatientId_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/notes/{patId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
