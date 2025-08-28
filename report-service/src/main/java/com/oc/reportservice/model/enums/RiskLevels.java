package com.oc.reportservice.model.enums;

import lombok.Getter;

@Getter
public enum RiskLevels {
    // _______________________________________________
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset");
    // _______________________________________________

    private final String label;

    RiskLevels(String label) {
        this.label = label;
    }
}
