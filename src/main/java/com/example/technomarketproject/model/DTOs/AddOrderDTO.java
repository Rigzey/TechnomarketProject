package com.example.technomarketproject.model.DTOs;

import com.example.technomarketproject.model.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddOrderDTO {
    private LocalDate orderDate;
    private String deliveryAddress;
    private double totalPrice;
    private List<Product> products;
}
