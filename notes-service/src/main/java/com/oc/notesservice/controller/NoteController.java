package com.oc.notesservice.controller;

import com.oc.notesservice.model.Note;
import com.oc.notesservice.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

    // create
    @PostMapping
    public ResponseEntity<String> createNote(@RequestBody Note note) {
        service.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body("Note created");
    }

    // read
    @GetMapping("/patient/{id}")
    public List<Note> getPatientNotes(@PathVariable String id) {
        return service.getPatientNotes(id);
    }
    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable String id) {
        return service.getNoteById(id);
    }

    // update (no need)

    // delete (for postman use)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) {
        Optional<Note> note = service.getNoteById(id);
        if (note.isPresent()) {
            service.deleteNote(id);
            return ResponseEntity.status(HttpStatus.OK).body("Note deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
    }

}
