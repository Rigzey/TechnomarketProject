package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 45, message = "Invalid product name size")
    private String name;
    private int subcategoryId;
    @Min(value = 0, message = "The price of a product cannot be negative")
    private double price;
    @Size(min = 1, max = 200, message = "Invalid description size")
    private String description;
    @NotNull(message = "The product must have characteristics")
    private List<CharacteristicWithValuesDTO> characteristics;
}
