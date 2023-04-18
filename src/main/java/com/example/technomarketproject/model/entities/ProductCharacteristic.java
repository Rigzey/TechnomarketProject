package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_characteristics")
public class ProductCharacteristic {
    @EmbeddedId
    private ProductCharacteristicKey id;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @MapsId("characteristicId")
    @JoinColumn(name = "characteristic_id")
    private Characteristic characteristic;
    private String value;
}
