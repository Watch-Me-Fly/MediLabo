package com.oc.frontend.controllers.patients;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Note;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/patients")
public class PatientDetailsController {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public PatientDetailsController(RestTemplate restTemplate, EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    // get patient's details for update form
    @GetMapping("/{id}")
    public String patientDetails(Model model, @PathVariable("id") String id) {

        // fetch patient
        String patientUrl = endpoints.getPatientService() + "/" + id;
        Patient patient = restTemplate.getForObject(patientUrl, Patient.class);
        model.addAttribute("patient", patient);

        // fetch notes
        String notesURL = endpoints.getNotesService() + "/patient/" + id;
        Note[] notes = restTemplate.getForObject(notesURL, Note[].class);
        model.addAttribute("notes", notes);

        return "patient/details";
    }

    // handle form submission
    @PostMapping("/{id}")
    public String updatePatient(@PathVariable("id") String id, @ModelAttribute Patient patient) {
        String url = endpoints.getPatientService() + "/" + id;
        restTemplate.put(url, patient);
        return "redirect:/patients/" + id;
    }

}
