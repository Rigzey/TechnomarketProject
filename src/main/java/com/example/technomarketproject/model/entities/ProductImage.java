package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "product_images")
@Getter
@Setter
public class ProductImage {

    @EmbeddedId
    private ProductImageKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image", insertable = false, updatable = false)
    private String image;
}
