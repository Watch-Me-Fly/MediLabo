package com.oc.frontend.controllers.notes;

import com.oc.frontend.config.EndpointsProperties;
import com.oc.frontend.models.Note;
import com.oc.frontend.models.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NewNoteController {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public NewNoteController(RestTemplate restTemplate,
                             EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    @GetMapping("/add/{patientId}")
    public String addNote(@PathVariable String patientId, Model model) {
        // new note
        Note note = new Note();
        note.setPatientId(patientId);
        // get patient details
        String patientUrl = endpoints.getPatientService() + "/" + patientId;
        Patient patient = restTemplate.getForObject(patientUrl, Patient.class);

        // add all to model
        model.addAttribute("note", note);
        model.addAttribute("patient", patient);
        model.addAttribute("currentTime", java.time.LocalDateTime.now());

        return "notes/addNote";
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note,
                             RedirectAttributes redirectAttributes) {
        String url = endpoints.getNotesService();
        restTemplate.postForObject(url, note, String.class);

        redirectAttributes.addFlashAttribute("SUCCESS", "Note created successfully!");
        return "redirect:/patients/" + note.getPatientId();
    }
}
