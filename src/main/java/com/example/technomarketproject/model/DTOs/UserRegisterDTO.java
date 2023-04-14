package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class UserRegisterDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private char gender;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private boolean emailSubscription;

}
