package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "characteristics")
@Getter
@Setter
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(name = "product_characteristics",
                joinColumns = @JoinColumn(name = "characteristic_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
