package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    void deleteByImage(String fileName);

    Optional<List<ProductImage>> findAllByProduct(Product p);
    void deleteAllByProduct(Product p);
}
