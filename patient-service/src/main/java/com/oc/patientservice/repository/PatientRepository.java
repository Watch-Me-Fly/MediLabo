package com.oc.patientservice.repository;

import com.oc.patientservice.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    Optional<Patient> findByFirstNameAndLastNameAndBirthDate(String firstName,
                                                               String lastName,
                                                               LocalDate dateOfBirth);
}
