package com.oc.reportservice.service;

import com.oc.reportservice.model.DTO.Note;
import com.oc.reportservice.model.DTO.Patient;
import com.oc.reportservice.model.ReportResults;
import com.oc.reportservice.model.enums.RiskLevels;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RiskAssessmentServiceTest {

    @Mock
    private PatientClient patientClient;
    @Mock
    private NotesClient notesClient;

    private RiskAssessmentService service;

    private static LocalDate riskyAge;
    private static LocalDate youngAge;
    private static Patient malePatient;
    private static Patient femalePatient;

    @BeforeAll
    public static void init() {
        riskyAge = LocalDate.of(1990, 1, 1);
        youngAge = LocalDate.of(2000, 1, 1);
    }
    @BeforeEach
    void setUp() {
        patientClient = Mockito.mock(PatientClient.class);
        notesClient = Mockito.mock(NotesClient.class);
        service = new RiskAssessmentService(patientClient, notesClient);

        // set up patients
        malePatient = new Patient(
                "1", "Jack", "Sparrow",
                youngAge, "M", null, null);
        femalePatient = new Patient(
                "2", "Elizabeth", "Swann",
                youngAge, "F", "Port Royal", null);
    }

    @DisplayName("No triggers in note [none]")
    @Test
    void getRiskAssessment_NoTriggers() {
        // arrange
        String noteCR = "Patient is ok";
        Note note = new Note("1", "1", noteCR, Instant.now());

        when(patientClient.getPatient(any())).thenReturn(malePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("1");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.NONE.name());
    }

    @DisplayName("3 triggers in the note, risky age, any sex [borderline]")
    @Test
    void getRiskAssessment_Borderline() {
        // arrange
        String noteCR = "fumeur, vertiges, hémoglobine A1C";
        Note note = new Note("1", "1", noteCR, Instant.now());
        malePatient.setBirthDate(riskyAge);

        when(patientClient.getPatient(any())).thenReturn(malePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("1");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.BORDERLINE.name());
    }

    @DisplayName("Young male patient, 3 triggers [in danger]")
    @Test
    void getRiskAssessment_MaleInDanger() {
        // arrange
        String noteCR = "Le patient est un fumeur, il se plaint des vertiges, " +
                        "et a un hémoglobine A1C de 7g";
        Note note = new Note("1", "1", noteCR, Instant.now());

        when(patientClient.getPatient(any())).thenReturn(malePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("1");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.IN_DANGER.name());
    }

    @DisplayName("Young female patient, 4 triggers [in danger]")
    @Test
    void getRiskAssessment_FemaleInDanger() {
        // arrange
        String noteCR = "Le patient est une fumeuse, elle se plaint des vertiges, " +
                        "et a un hémoglobine A1C de 7g + Poids 90kg";
        Note note = new Note("1", "2", noteCR, Instant.now());

        when(patientClient.getPatient(any())).thenReturn(femalePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("2");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.IN_DANGER.name());
    }

    @DisplayName("6 triggers, risky age, any sex [in danger]")
    @Test
    void getRiskAssessment_InDanger() {
        // arrange
        String noteCR = "fumeuse, vertiges, microalbumine, taille, poids anormal";
        Note note = new Note("1", "1", noteCR, Instant.now());
        malePatient.setBirthDate(riskyAge);

        when(patientClient.getPatient(any())).thenReturn(malePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("1");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.IN_DANGER.name());
    }

    @DisplayName("Young male, 5 triggers [early onset]")
    @Test
    void getRiskAssessment_EarlyOnsetMale() {
        // arrange
        String noteCR = "fumeur, vertiges, microalbumine, taille, poids";
        Note note = new Note("1", "1", noteCR, Instant.now());

        when(patientClient.getPatient(any())).thenReturn(malePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("1");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.EARLY_ONSET.name());
    }
    @DisplayName("Young female, 7 triggers [early onset]")
    @Test
    void getRiskAssessment_EarlyOnsetFemale() {
        // arrange
        String noteCR = "fumeur, vertiges, microalbumine, taille, poids anormal, réaction";
        Note note = new Note("1", "2", noteCR, Instant.now());

        when(patientClient.getPatient(any())).thenReturn(femalePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("2");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.EARLY_ONSET.name());
    }

    @DisplayName("8 triggers, any sex, risky age [early onset]")
    @Test
    void getRiskAssessment_EarlyOnset() {
        // arrange
        String noteCR = "fumeur, vertiges, microalbumine, taille, poids anormal, réaction, rechute";
        Note note = new Note("1", "2", noteCR, Instant.now());
        femalePatient.setBirthDate(riskyAge);

        when(patientClient.getPatient(any())).thenReturn(femalePatient);
        when(notesClient.getNotesByPatientId(any())).thenReturn(List.of(note));
        // act
        ReportResults results = service.generateRiskReport("2");
        // assert
        assertThat(results.getRiskLevel()).isEqualTo(RiskLevels.EARLY_ONSET.name());
    }

}
