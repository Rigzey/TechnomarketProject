package com.example.technomarketproject.model.DTOs;

import com.example.technomarketproject.model.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewSubcategoryDTO {
    private String name;
    private int categoryId;
}
