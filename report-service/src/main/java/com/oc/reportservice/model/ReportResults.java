package com.oc.reportservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ReportResults {

    private String patientId;
    private String patientName;
    private int patientAge;
    private String riskLevel;

}