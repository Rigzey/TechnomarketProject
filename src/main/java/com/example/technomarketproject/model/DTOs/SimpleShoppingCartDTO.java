package com.example.technomarketproject.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleShoppingCartDTO {
    private int id;
    private UserWithoutPasswordDTO user;
    private SimpleProductDTO productId;
    private int quantity;
}
