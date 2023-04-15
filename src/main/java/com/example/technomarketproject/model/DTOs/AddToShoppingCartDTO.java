package com.example.technomarketproject.model.DTOs;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddToShoppingCartDTO {
    private Product productId;
    private int quantity;
}
