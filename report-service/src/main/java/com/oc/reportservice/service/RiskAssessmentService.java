package com.oc.reportservice.service;

import com.oc.reportservice.model.DTO.Note;
import com.oc.reportservice.model.DTO.Patient;
import com.oc.reportservice.model.ReportResults;
import com.oc.reportservice.model.enums.RiskLevels;
import com.oc.reportservice.model.enums.Triggers;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class RiskAssessmentService {

    private final PatientClient patientClient;
    private final NotesClient notesClient;

    public RiskAssessmentService(PatientClient patientClient,
                                 NotesClient notesClient) {
        this.patientClient = patientClient;
        this.notesClient = notesClient;
    }

    // logic
    public ReportResults generateRiskReport(String patientId) {
        // get patient data
        Patient patient = patientClient.getPatient(patientId);
        String fullName = patient.getFirstName() + " " + patient.getLastName();

        // get the notes
        List<Note> notes = notesClient.getNotesByPatientId(patientId);

        // calculate the age (to count as a risk factor)
        int age = calculateAge(patient.getBirthDate());

        // count number of triggers
        int triggers = countTriggersInAllNotes(notes);

        // assess risk
        String risk = determineRiskLevel(patient.getSex(), age, triggers);

        // return results
        return new ReportResults(patientId, fullName, age, risk);
    }
    private String determineRiskLevel(String sex, int age, int triggers) {
        boolean male = sex.equalsIgnoreCase("M");
        boolean female = sex.equalsIgnoreCase("F");
        boolean riskyAge = age >= 30;

        if (triggers == 0) {
            return RiskLevels.NONE.name();
        }
        // above 30 (male or female)
        if (riskyAge) {

            if (triggers >= 2 && triggers <= 5) {
                return RiskLevels.BORDERLINE.name();
            }
            if ((triggers == 6 || triggers == 7)) {
                return RiskLevels.IN_DANGER.name();
            }
            if (triggers >= 8) {
                return RiskLevels.EARLY_ONSET.name();
            }
        }
        // under 30
        if (!riskyAge) {

            if (male) {
                if (triggers == 3) return RiskLevels.IN_DANGER.name();
                if (triggers >= 5) return RiskLevels.EARLY_ONSET.name();
            }

            if (female) {
                if (triggers == 4) return RiskLevels.IN_DANGER.name();
                if (triggers >= 7) return RiskLevels.EARLY_ONSET.name();
            }

        }
        return RiskLevels.NONE.name();
    }

    // calculations
    private int countTriggersInAllNotes(List<Note> notes) {
        // create a list of completable futures
        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        // for each note, create a completable future to hold trigger count
        for (Note note : notes) {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
                    countTriggersInANote(note));
            futures.add(future);
        }

        // at the end of all calculations : create a single future to avoid waiting
        CompletableFuture<Void> completed = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        // collect the results
        try {
            completed.get();
            // sum up the results
            int total = 0;
            for (CompletableFuture<Integer> future : futures) {
                total += future.join();
            }
            return total;
        } catch (Exception e) {
            String errMsg = "Error occurred while counting triggers in a list of notes";
            throw new RuntimeException(errMsg, e);
        }

    }
    private int countTriggersInANote(Note note) {
        String text = note.getTextContent().toLowerCase(Locale.ROOT);
        int count = 0;
        for (Triggers trigger : Triggers.values()) {
            if (trigger.matches(text)) {
                count++;
            }
        }
        return count;
    }
    private int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}