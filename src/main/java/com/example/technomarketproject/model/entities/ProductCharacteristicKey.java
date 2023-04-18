package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductCharacteristicKey implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "characteristic_id")
    private int characteristicId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCharacteristicKey that = (ProductCharacteristicKey) o;
        return productId == that.productId && characteristicId == that.characteristicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, characteristicId);
    }
}
