package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleProductDTO {
    private int id;
    private String name;
    private SimpleSubcategoryDTO subcategory;
    private double price;
    private String description;
    private List<SimpleProductCharacteristicDTO> characteristics;
    private List<ReviewWithoutProductDTO> reviews;
    private List<ProductImageDTO> productImages;

}
