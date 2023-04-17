package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveFromCartDTO {
    @Min(value = 1, message = "Must remove at least 1 product")
    private int quantity;
}
