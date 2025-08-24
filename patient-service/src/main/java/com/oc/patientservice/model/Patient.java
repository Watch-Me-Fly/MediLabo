package com.oc.patientservice.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Patient {

    @Id
    private String id;

    @NotBlank(message = "first name is mandatory")
    private String firstName;

    @NotBlank(message = "last name is mandatory")
    private String lastName;

    @NotNull(message = "date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotBlank(message = "sex is mandatory")
    @Pattern(regexp = "M|F|Other")
    private String sex;

    @Nullable
    private String address;

    @Nullable
    private String phone;

}
