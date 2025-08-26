package com.oc.frontend.controllers.patients;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{id}")
    public String patientDetails(Model model, @PathVariable("id") String id) {

        String url = endpoints.getPatientService() + "/" + id;
        Patient patient = restTemplate.getForObject(url, Patient.class);

        model.addAttribute("patient", patient);
        return "patient/details";
    }

}
