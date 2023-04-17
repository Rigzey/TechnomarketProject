package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddToShoppingCartDTO {
    private ProductWithIdOnlyDTO product;
    @Min(value =  1, message = "Must add at least one product")
    private int quantity;
}
