package com.oc.notesservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Document(collection = "notes")
public class Note {

    @Id
    private String id;
    private String patientId;
    private String textContent;
    private Instant createdAt = Instant.now();

}
