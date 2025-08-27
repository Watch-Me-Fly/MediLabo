package com.oc.frontend.controllers.patients;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class NewPatientController {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public NewPatientController(RestTemplate restTemplate, EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    @GetMapping("/add")
    public String showCreateForm(
            Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/newPatient";
    }

    @PostMapping
    public String createPatient(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
        String url = endpoints.getPatientService();

        restTemplate.postForObject(url, patient, String.class);

        redirectAttributes.addFlashAttribute("success",
                "Patient profile created successfully!");
        return "redirect:/patients";
    }
}
