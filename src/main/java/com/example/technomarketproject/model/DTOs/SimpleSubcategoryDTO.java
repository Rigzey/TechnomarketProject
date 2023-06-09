package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSubcategoryDTO extends SubcategoryWithNameIdDTO {
    private int id;
    private CategoryWithNameIdDTO category;
    private String name;
}
