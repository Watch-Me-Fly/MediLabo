package com.oc.frontend.controllers.patients;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/patients")
public class PatientsListController {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public PatientsListController(RestTemplate restTemplate, EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    @GetMapping
    public String patientsList(Model model) {

        Patient[] patients = restTemplate
                            .getForObject(endpoints.getPatientService(), Patient[].class);

        model.addAttribute("patients", patients);
        return "patient/patients";
    }

}