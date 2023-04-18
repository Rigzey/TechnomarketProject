package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShoppingCartKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartKey that = (ShoppingCartKey) o;
        return userId == that.userId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
