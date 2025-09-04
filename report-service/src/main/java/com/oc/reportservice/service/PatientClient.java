package com.oc.reportservice.service;

import com.oc.reportservice.config.EndpointsProperties;
import com.oc.reportservice.model.DTO.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientClient {

    private final RestTemplate restTemplate;
    private final EndpointsProperties endpoints;

    public PatientClient(RestTemplate restTemplate,
                         EndpointsProperties endpoints) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
    }

    public Patient getPatient(String patientId) {
        String url = endpoints.getPatientService() + "/" + patientId;
        return restTemplate.getForObject(url, Patient.class);
    }

}
