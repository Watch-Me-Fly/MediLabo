package com.oc.frontend.controllers.patients;

import com.oc.frontend.models.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/patients")
public class PatientsListController {

    private final RestTemplate restTemplate;
    private final String patientsURL;

    public PatientsListController(RestTemplateBuilder builder,
                                  @Value("${app.endpoints.patient-service}") String patientsURL) {
        this.restTemplate = builder.build();
        this.patientsURL = patientsURL;
    }

    @GetMapping
    public String patientsList(Model model) {

        Patient[] patients = restTemplate
                            .getForObject(patientsURL, Patient[].class);

        model.addAttribute("patients", patients);
        return "patient/patients";
    }

}