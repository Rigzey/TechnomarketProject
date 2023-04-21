package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT DISTINCT p " +
            "FROM products p " +
            "LEFT JOIN subcategories sc ON p.subcategory.id = sc.id " +
            "LEFT JOIN categories c ON sc.category.id = c.id " +
            "LEFT JOIN product_characteristics pc ON p.id = pc.product.id " +
            "LEFT JOIN characteristics ch ON pc.characteristic.id = ch.id " +
            "LEFT JOIN product_images pi ON p.id = pi.product.id " +
            "WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:subcategoryId IS NULL OR sc.id = :subcategoryId) " +
            "AND (:subcategoryName IS NULL OR sc.name = :subcategoryName) " +
            "AND (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:categoryName IS NULL OR c.name = :categoryName) " +
            "AND (:priceFrom IS NULL OR p.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR p.price <= :priceTo) " +
            "AND (:description IS NULL OR p.description LIKE %:description%) " +
            "AND ((:characteristicIds IS NULL OR pc.characteristic.id IN :characteristicIds) " +
            "AND (:characteristicValues IS NULL OR pc.value IN :characteristicValues)) ")
    List<Product> findByMultipleCharacteristics(
            @Param("name") Optional<String> name,
            @Param("subcategoryId") Optional<Integer> subcategoryId,
            @Param("subcategoryName") Optional<String> subcategoryName,
            @Param("categoryId") Optional<Integer> categoryId,
            @Param("categoryName") Optional<String> categoryName,
            @Param("priceFrom") Optional<Double> priceFrom,
            @Param("priceTo") Optional<Double> priceTo,
            @Param("description") Optional<String> description,
            @Param("characteristicIds") Optional<List<Integer>> characteristicIds,
            @Param("characteristicValues") Optional<List<String>> characteristicValues);
}