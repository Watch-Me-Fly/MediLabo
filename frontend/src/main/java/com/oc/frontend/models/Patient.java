package com.oc.frontend.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Patient {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String sex;
    private String address;
    private String phone;

}
