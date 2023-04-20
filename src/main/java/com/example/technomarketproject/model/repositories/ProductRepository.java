package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    List<Product> findAllByNameContainingIgnoreCase(String name);
}
