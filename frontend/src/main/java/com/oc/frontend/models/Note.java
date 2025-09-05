package com.oc.frontend.models;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Note {

    private String id;
    private String patientId;
    private String textContent;
    private Instant createdAt = Instant.now();

}
