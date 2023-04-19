package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "product_images")
public class ProductImage {

    @EmbeddedId
    private ProductImageKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String imageUrl;
}
