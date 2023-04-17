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
public class CharacteristicWithValuesDTO {
    private int id;
    @Size(min = 1, max = 100, message = "Invalid value size")
    private String value;
}
