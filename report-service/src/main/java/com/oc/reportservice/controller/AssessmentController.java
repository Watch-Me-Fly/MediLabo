package com.oc.reportservice.controller;

import com.oc.reportservice.model.ReportResults;
import com.oc.reportservice.service.RiskAssessmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class AssessmentController {

    private final RiskAssessmentService service;

    public AssessmentController(RiskAssessmentService service) {
        this.service = service;
    }

    @GetMapping("/{patientId}")
    public ReportResults assessRisk(@PathVariable String patientId) {
        return service.generateRiskReport(patientId);
    }

}