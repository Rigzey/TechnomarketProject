package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPasswordDTO {

    private int id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    @Size(min = 6, max = 100, message = "Email must be between 6 and 100 characters long")
    private String email;

    @Pattern(regexp = "^[mf]$", message = "Invalid gender")
    private char gender;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Date of birth cannot be blank")
    @Past(message = "Invalid date of birth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^0[89][0-9]{8}$", message = "Invalid phone number")
    private String phoneNumber;

    private String address;
}
