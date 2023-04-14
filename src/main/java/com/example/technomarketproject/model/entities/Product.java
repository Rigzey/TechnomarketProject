package com.example.technomarketproject.model.entities;

import com.example.technomarketproject.model.DTOs.CharacteristicsWithValuesDTO;
import lombok.*;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;

@Entity(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "product")
    private List<ProductCharacteristic> values;
}
