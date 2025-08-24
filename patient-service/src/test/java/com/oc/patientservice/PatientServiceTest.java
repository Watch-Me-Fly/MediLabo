package com.oc.patientservice;

import com.oc.patientservice.model.Patient;
import com.oc.patientservice.repository.PatientRepository;
import com.oc.patientservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {

    @Mock
    private static PatientRepository repository;
    @InjectMocks
    private static PatientService service;

    private List<Patient> patients;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PatientService(repository);
        patients = new ArrayList<>();
        patients.add(new Patient(
                "1", "Jack", "Sparrow",
                LocalDate.of(1993, 2, 1),
                "M", "Black Pearl", null));
        patients.add(new Patient(
                "2", "Elizabeth", "Swann",
                LocalDate.of(1990, 1, 2),
                "F", "Port Royal", null));

    }
    // create
    @DisplayName("Create a new patient profile")
    @Test
    public void createPatientProfile_success() {
        // arrange
        Patient patient = new Patient();
        patient.setId("3");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDateOfBirth(LocalDate.of(1945, 10, 1));
        patient.setSex("M");

        when(repository.save(patient)).thenReturn(patient);
        when(repository.findById(any())).thenReturn(Optional.of(patient));
        // act
        service.createPatient(patient);
        // assert
        Optional<Patient> retrievePatient = service.findById("3");

        verify(repository, times(1)).save(patient);
        assertEquals("3", retrievePatient.get().getId());
    }
    @DisplayName("Should not create a patient profile due to missing data")
    @Test
    public void createPatientProfile_missingData() {
        // arrange
        Patient patient = new Patient();
        patient.setId("3");
        patient.setLastName("Doe");
        patient.setDateOfBirth(LocalDate.of(1945, 10, 1));
        patient.setSex("M");

        when(repository.save(patient)).thenReturn(patient);
        // act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.createPatient(patient)
        );
        assertEquals("first name is mandatory", exception.getMessage());
    }
    // update
    @DisplayName("Test getting the list of all patients")
    @Test
    public void getAllPatients() {
        // arrange
        when(repository.findAll()).thenReturn(patients);
        // act
        List<Patient> findPatients = service.findAll();
        // assert
        assertEquals(2, findPatients.size());
    }
    @DisplayName("Get information of a single patient")
    @Test
    public void getPatientInfo_success() {
        // arrange
        when(repository
                .findByFirstNameAndLastNameAndDateOfBirth(any(), any(), any()))
                .thenReturn(
                        Optional.ofNullable(patients.getLast())
                );
        // act
        Optional<Patient> patient = service.getPatientInformation(
                "Elizabeth", "Swann", LocalDate.of(1990, 1, 2)
        );
        // assert
        verify(repository, times(1))
                .findByFirstNameAndLastNameAndDateOfBirth(any(), any(), any());
        assertEquals("Elizabeth", patient.get().getFirstName());
    }
    // update
    @DisplayName("Update patient information successfully")
    @Test
    public void updatePatient_success() {
        // arrange
        Patient patient = patients.getFirst();
        patient.setFirstName("name");

        when(repository.existsById(any())).thenReturn(true);
        when(repository.save(any())).thenReturn(patients.getFirst());
        // act
        service.updatePatient(patients.getFirst());
        // assert
        assertEquals("name", patients.getFirst().getFirstName());
    }
    // delete
    @DisplayName("Delete patient's profile")
    @Test
    public void deletePatientProfile_success() {
        // arrange
        when(repository.existsById(any())).thenReturn(true);
        // act
        service.deletePatient("2");
        // assert
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).deleteById(any());
    }

}
