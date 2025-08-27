package com.oc.frontend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Note {

    private String id;
    private String patientId;
    private String textContent;
    private Instant createdAt = Instant.now();

}
