package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddProductDTO {
    private String name;
    private int subcategoryId;
    private double price;
    private String description;
    private List<CharacteristicWithValuesDTO> characteristics;
}
