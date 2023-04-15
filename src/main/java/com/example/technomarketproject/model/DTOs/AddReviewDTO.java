package com.example.technomarketproject.model.DTOs;

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
    private String title;
    private String comment;
    private double rating;
}
