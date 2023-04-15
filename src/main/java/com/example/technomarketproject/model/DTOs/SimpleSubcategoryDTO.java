package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleSubcategoryDTO {
    private int id;
    private String name;
    private CategorySimpleDTO category;
}
