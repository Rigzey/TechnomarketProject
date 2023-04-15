package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleProductCharacteristicDTO {
    private int id;
    private CharacteristicWithValuesDTO characteristic;
}
