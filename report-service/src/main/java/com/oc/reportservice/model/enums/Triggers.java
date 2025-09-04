package com.oc.reportservice.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
public enum Triggers {
    // _______________________________________________
    HEMOGLOBINE_A1C("hémoglobine a1c"),
    MICROALBUMINE("microalbumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur", "fumeuse"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholestérol"),
    VERTIGES("vertiges"),
    RECHUTE("rechute"),
    REACTION("réaction"),
    ANTICORPS("anticorps");
    // _______________________________________________

    private final List<String> keywords;

    Triggers(String... keywords) {
        this.keywords = Arrays.stream(keywords)
                              .map(String::toLowerCase)
                              .collect(Collectors.toList());
    }

    public boolean matches(String text) {
        String normalizedText = text.toLowerCase(Locale.ROOT);
        return keywords.stream()
                       .anyMatch(normalizedText::contains);
    }
}
