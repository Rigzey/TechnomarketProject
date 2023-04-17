package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSubcategoryDTO {
    @Size(min = 1, max = 45, message = "Invalid name size!")
    private String name;
    private int categoryId;
}
