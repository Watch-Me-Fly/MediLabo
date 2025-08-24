package com.oc.patientservice.controller;

import com.oc.patientservice.model.Patient;
import com.oc.patientservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // create
    @PostMapping
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        service.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient profile created");
    }
    // read
    @GetMapping
    public List<Patient> getAllPatients() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable String id) {
        return service.findById(id);
    }
    @GetMapping("/search")
    public Optional<Patient> getPatientInformation(@RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String dateOfBirth) {

        LocalDate birthDate = LocalDate.parse(dateOfBirth);
        return service.getPatientInformation(firstName, lastName, birthDate);
    }
    // update
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable String id,
                                                @RequestBody Patient patient) {
        patient.setId(id);
        service.updatePatient(patient);
        return ResponseEntity.status(HttpStatus.OK).body("Patient information updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable String id) {
        Optional<Patient> patient = service.findById(id);
        if (patient.isPresent()) {
            service.deletePatient(id);
            return ResponseEntity.status(HttpStatus.OK).body("Patient profile deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
    }

}
