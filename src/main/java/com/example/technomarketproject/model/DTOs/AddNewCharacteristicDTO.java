package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddNewCharacteristicDTO {
    @Size(min = 1, max = 45, message = "Invalid characteristic name size")
    private String name;
}
