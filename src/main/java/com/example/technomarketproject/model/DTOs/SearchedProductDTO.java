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
public class SearchedProductDTO {
    private int id;
    private String name;
    private double price;
    private double rating;
    private List<ProductImageDTO> productImages;
    private SimpleSubcategoryDTO subcategory;
}
