package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ProductImageKey implements Serializable {

    @Column(name = "product_id")
    private int productId;

    @Column(name = "image")
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageKey that = (ProductImageKey) o;
        return productId == that.productId && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, url);
    }
}
