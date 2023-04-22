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
    @Email(message = "Invalid email address")
    @Size(max = 100, message = "Email size too big")
    private String email;

    @Pattern(regexp = "[m|M|f|F]", message = "Invalid gender")
    private String gender;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Past(message = "Invalid date of birth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^0[89][0-9]{8}$", message = "Invalid phone number")
    private String phoneNumber;
    @NotNull(message = "Address cannot be null")
    @Size(max = 200, message = "Invalid address size")
    private String address;
}
