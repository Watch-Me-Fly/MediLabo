package com.oc.frontend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {

    private String patientId;
    private String patientName;
    private int patientAge;
    private String riskLevel;
}
