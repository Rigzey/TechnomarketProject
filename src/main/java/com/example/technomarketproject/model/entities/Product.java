package com.example.technomarketproject.model.entities;

import lombok.*;
import jakarta.persistence.*;

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

    @Column
    private String productImageUrl;

    @OneToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "product")
    private List<ProductCharacteristic> values;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @OneToMany(mappedBy = "productId")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favourites")
    private List<User> favouriteOfUsers;
}
