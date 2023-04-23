package com.example.technomarketproject.model.entities;

import lombok.*;
import jakarta.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shopping_cart")
public class ShoppingCart {
    @EmbeddedId
    private ShoppingCartKey id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
