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

    private String name;

    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCharacteristic> values;

}
