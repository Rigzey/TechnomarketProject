package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProductCharacteristicKey implements Serializable {
    @Column(name = "product_id")
    int productId;
    @Column(name = "characteristic_id")
    int characteristicId;


}
