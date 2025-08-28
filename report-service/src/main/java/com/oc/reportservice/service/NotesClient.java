package com.oc.reportservice.service;

import com.oc.reportservice.config.EndpointsProperties;
import com.oc.reportservice.model.DTO.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NotesClient {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public NotesClient(RestTemplate restTemplate,
                         EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    public List<Note> getNotesByPatientId(String patientId) {
        String url = endpoints.getNotesService() + "/patient/" + patientId;
        Note[] notes = restTemplate.getForObject(url, Note[].class);
        return notes != null ? Arrays.asList(notes) : List.of();
    }

}
