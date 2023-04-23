package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilteringDTO {
    private Integer subcategoryId;
    private Integer categoryId;
    private Integer ratingFrom;
    private Double priceFrom;
    private Double priceTo;
    private String description;
}
