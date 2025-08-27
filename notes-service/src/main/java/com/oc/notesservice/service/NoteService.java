package com.oc.notesservice.service;

import com.oc.notesservice.model.Note;
import com.oc.notesservice.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    Logger log = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // create
    public void createNote(Note note) {
        log.info("[NoteService] - entered createNote");
        try {
            repository.save(note);
            log.info("[NoteService] - note created");
        } catch (Exception e) {
            log.error("[NoteService] - error creating note :" + e);
        }
    }

    // read
    public List<Note> getPatientNotes(String patientId) {
        log.info("[NoteService] - entered getPatientNotes");

        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("Invalid patient id");
        }

        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            log.error("[NoteService] - error getting patientNotes :" + e);
            throw new RuntimeException("Error retrieving patient's notes : " + e);
        }
    }
    public Optional<Note> getNoteById(String id) {
        log.info("[NoteService] - entered getNoteById");

        try {
            return repository.findById(id);
        } catch (Exception e) {
            log.error("[NoteService] - error getting note by id :" + e);
            throw new RuntimeException("Error retrieving note by id :" + e);
        }
    }
    // update (no need)

    // delete (for postman use)
    public void deleteNote(String id) {
        log.info("[NoteService] - entered deleteNote");

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid note id");
        }

        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
                log.info("[NoteService] - note deleted");
            } catch (Exception e) {
                log.error ("[NoteService] - error deleting note :{}", String.valueOf(e));
            }
        } else {
            throw new IllegalArgumentException("Note not found");
        }
    }

}
