package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_characteristics")
public class ProductCharacteristic {
    @EmbeddedId
    ProductCharacteristicKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("characteristicId")
    @JoinColumn(name = "characteristic_id")
    Characteristic characteristic;

    @Column
    String value;
}
