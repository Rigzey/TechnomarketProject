package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleSubcategoryDTO extends SubcategoryWithNameIdDTO {
    private CategoryWithNameIdDTO category;
}
