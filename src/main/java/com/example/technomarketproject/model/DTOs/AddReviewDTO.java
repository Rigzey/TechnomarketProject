package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDTO {
    private ProductWithIdOnlyDTO productId;
    @Size(min = 1, max = 45, message = "Title size invalid")
    private String title;
    @Size(min = 1, max = 200, message = "Invalid comment size")
    private String comment;
    @Min(value = 1, message = "Rating must be between 1 and 10")
    @Max(value = 10, message = "Rating must be between 1 and 10")
    private double rating;
}
