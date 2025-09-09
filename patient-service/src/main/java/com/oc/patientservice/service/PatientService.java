package com.oc.patientservice.service;

import com.oc.patientservice.model.Patient;
import com.oc.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    // create
    public void createPatient(Patient patient) {
        log.info("[PatientService] - entered createPatient");
        if (patient.getFirstName() == null
                || patient.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("first name is mandatory");
        }
        if (patient.getLastName() == null
                || patient.getLastName().isEmpty()) {
            throw new IllegalArgumentException("last name is mandatory");
        }
        if (patient.getBirthDate() == null) {
            throw new IllegalArgumentException("date of birth is mandatory");
        }
        if (patient.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("date of birth is after current date");
        }
        if (patient.getSex() == null
                || patient.getSex().isEmpty()) {
            throw new IllegalArgumentException("sex is mandatory");
        }
        try {
            repository.save(patient);
            log.info("[PatientService] - Exit createPatient");
        }
        catch (Exception e) {
            log.error("[PatientService] - error creating patient :" + e);
        }
    }
    // read
    public List<Patient> findAll() {
        log.info("[PatientService] - entered findAll");
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("[PatientService] - error finding all patients");
            throw new RuntimeException("Error retrieving all patients list : " + e);
        }
    }
    public Optional<Patient> findById(Long id) {
        log.info("[PatientService] - entered findById");
        try {
            return repository.findById(id);
        } catch (Exception e) {
            log.error("[PatientService] - error finding patient by id :" + e);
            throw new RuntimeException("Error retrieving patient by id :" + e);
        }
    }
    public Optional<Patient> getPatientInformation(String firstName,
                                                   String lastName,
                                                   LocalDate dateOfBirth) {
        log.info("[PatientService] - entered getPatientInformation");
        try {
            return repository.findByFirstNameAndLastNameAndBirthDate(
                    firstName, lastName, dateOfBirth);
        } catch (Exception e) {
            log.error("[PatientService] - error getting patient information :" + e);
            throw new RuntimeException("Error retrieving patient information :" + e);
        }
    }
    // update
    public void updatePatient(Patient patient) {
        log.info("[PatientService] - entered updatePatient");
        if (repository.existsById(patient.getId())) {
            try {
                repository.save(patient);
                log.info("[PatientService] - exit updatePatient");
            } catch (Exception e) {
                log.error("[PatientService] - error updating patient :" + e);
            }
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }
    // delete
    public void deletePatient(Long id) {
        log.info("[PatientService] - entered deletePatient");
        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
                log.info("[PatientService] - exit deletePatient");
            } catch (Exception e) {
                log.error("[PatientService] - error deleting patient :" + e);
            }
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }
}
