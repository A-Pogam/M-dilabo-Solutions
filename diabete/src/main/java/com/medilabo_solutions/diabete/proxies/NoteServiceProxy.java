package com.medilabo_solutions.diabete.proxies;

import com.medilabo_solutions.diabete.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "note-service", url = "http://localhost:8083/notes")
public interface NoteServiceProxy {
    @GetMapping("/{patId}")
    Note[] getNotesByPatientId(@PathVariable Long patId);
}
