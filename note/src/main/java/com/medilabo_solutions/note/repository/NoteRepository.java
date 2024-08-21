package com.medilabo_solutions.note.repository;

import com.medilabo_solutions.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatId(Long patId);

    void deleteByPatId(Long patId);

}
