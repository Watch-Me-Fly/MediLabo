package com.oc.notesservice.repository;

import com.oc.notesservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientId(String patientId);
}
