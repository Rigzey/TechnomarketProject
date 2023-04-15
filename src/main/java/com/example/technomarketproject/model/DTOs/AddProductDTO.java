package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO {
    private String name;
    private int subcategoryId;
    private double price;
    private String description;
    private List<CharacteristicWithValuesDTO> characteristics;
}
