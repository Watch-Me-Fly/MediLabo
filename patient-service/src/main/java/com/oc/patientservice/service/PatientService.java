package com.oc.patientservice.service;

import com.oc.patientservice.model.Patient;
import com.oc.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    // create
    public void createPatient(Patient patient) {
        if (patient.getFirstName() == null
                || patient.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("first name is mandatory");
        }
        if (patient.getLastName() == null
                || patient.getLastName().isEmpty()) {
            throw new IllegalArgumentException("last name is mandatory");
        }
        if (patient.getDateOfBirth() == null) {
            throw new IllegalArgumentException("date of birth is mandatory");
        }
        if (patient.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("date of birth is after current date");
        }
        if (patient.getSex() == null
                || patient.getSex().isEmpty()) {
            throw new IllegalArgumentException("sex is mandatory");
        }
        repository.save(patient);
    }
    // read
    public List<Patient> findAll() {
        return repository.findAll();
    }
    public Optional<Patient> findById(String id) {
        return repository.findById(id);
    }
    public Optional<Patient> getPatientInformation(String firstName,
                                                   String lastName,
                                                   LocalDate dateOfBirth) {
        return repository.findByFirstNameAndLastNameAndDateOfBirth(
                firstName, lastName, dateOfBirth);
    }
    // update
    public void updatePatient(Patient patient) {
        if (repository.existsById(patient.getId())) {
            repository.save(patient);
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }
    // delete
    public void deletePatient(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }
}
