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
public class UserRegisterDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Size(min = 6, max = 100, message = "Email must be between {min} and {max} characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least {min} characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password too weak. Must contain at least one upper case, lower case and number!")
    private String password;

    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;

    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "[m|M|f|F]", message = "Invalid gender")
    private String gender;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Invalid date of birth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^0[89][0-9]{8}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be null")
    @Size(max = 200, message = "Address length cannot be more than 200")
    private String address;
}
