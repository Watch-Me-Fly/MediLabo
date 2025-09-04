package com.oc.reportservice.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
