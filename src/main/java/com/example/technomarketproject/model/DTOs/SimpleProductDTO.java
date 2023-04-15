package com.example.technomarketproject.model.DTOs;

import com.example.technomarketproject.model.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SimpleProductDTO {
    private int id;
    private String name;
    private SimpleSubcategoryDTO subcategory;
    private double price;
    private String description;
    private List<SimpleProductCharacteristicDTO> values;
    private List<Review> reviews;
}
