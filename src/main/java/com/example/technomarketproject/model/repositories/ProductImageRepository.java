package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    //void deleteByUrl(String fileName);

    void deleteByImage(String fileName);

    List<ProductImage> findAllByProduct(Product product);
}
