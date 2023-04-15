package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleShoppingCartDTO {
    private UserWithoutPasswordDTO user;
    private SimpleProductDTO productId;
    private int id;
    private int quantity;
}
