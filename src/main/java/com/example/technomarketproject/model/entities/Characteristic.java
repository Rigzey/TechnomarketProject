package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity(name = "characteristics")
@Getter
@Setter
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column
    private double value;
    @ManyToMany
    @JoinTable(name = "product_characteristics",
                joinColumns = @JoinColumn(name = "characteristic_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
