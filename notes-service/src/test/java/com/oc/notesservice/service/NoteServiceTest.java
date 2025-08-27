package com.oc.notesservice.service;

import com.oc.notesservice.model.Note;
import com.oc.notesservice.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NoteServiceTest {

    @Mock
    private NoteRepository repository;
    @InjectMocks
    private NoteService service;

    private List<Note> notes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new NoteService(repository);
        notes = new ArrayList<>();
        notes.add(new Note(
                "1", "11", "First Note", new Date().toInstant()
        ));
        notes.add(new Note(
                "2", "22", "Second Note", new Date().toInstant()
        ));
        notes.add(new Note(
                "3", "11", "Third Note", new Date().toInstant()
        ));
    }

    @DisplayName("Create a note")
    @Test
    public void createNote() {
        // arrange
        Note note = new Note(
                "5", "11", "First Note", new Date().toInstant()
        );
        when(repository.save(note)).thenReturn(note);
        when(repository.findById(any())).thenReturn(Optional.of(note));
        // act
        service.createNote(note);
        // assert
        Optional<Note> retrieveNote = repository.findById("5");
        verify(repository, times(1)).save(note);
        assertEquals("5", retrieveNote.get().getId());
    }

    @DisplayName("Get a list of patient's notes")
    @Test
    public void getPatientNotes() {
        List<Note> patientNotes = List.of(notes.get(0), notes.get(2));
        // arrange
        when(repository.findByPatientId(any())).thenReturn(patientNotes);
        // act
        service.getPatientNotes("5");
        // assert
        verify(repository, times(1)).findByPatientId(any());
        assertEquals(2, patientNotes.size());
    }

    @DisplayName("Get a note by id")
    @Test
    public void getNoteById() {
        // arrange
        Note note = notes.getFirst();
        when(repository.findById(any()))
                .thenReturn(Optional.of(note));
        // act
        service.getNoteById("1");
        // assert
        verify(repository, times(1)).findById(any());
        assertEquals("11", note.getPatientId());
    }

    @DisplayName("Delete a note")
    @Test
    public void deleteNote() {
        Note note = notes.getFirst();
        // arrange
        when(repository.existsById(any())).thenReturn(true);
        // act
        service.deleteNote("1");
        // assert
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).deleteById(note.getId());
    }

}
