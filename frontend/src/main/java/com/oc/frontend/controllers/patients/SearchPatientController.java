package com.oc.frontend.controllers.patients;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class SearchPatientController {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public SearchPatientController(RestTemplate restTemplate, EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "patient/search";
    }

    @GetMapping("/search/results")
    public String patientSearch(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String birthDate,
                                RedirectAttributes redirectAttributes) {

        String url = endpoints.getPatientService()
                + "/search?firstName={firstName}&lastName={lastName}&dateOfBirth={birthDate}";

        Patient patient = restTemplate.getForObject(url, Patient.class,
                firstName, lastName, birthDate);

        if (patient == null || patient.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "No patient found");
            return "redirect:/patients/search?error=notfound";
        }

        return "redirect:/patients/" + patient.getId();
    }
}
