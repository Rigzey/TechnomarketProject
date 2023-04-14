package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
