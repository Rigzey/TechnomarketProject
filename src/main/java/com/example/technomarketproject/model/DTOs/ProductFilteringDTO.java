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
public class ProductFilteringDTO {
    private String name;
    private Integer subcategoryId;
    private Double priceFrom;
    private Double priceTo;
    private String description;
    private List<Integer> characteristicIds;
    private List<String> characteristicValues;
}
