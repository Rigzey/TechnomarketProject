package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    boolean existsByName(String name);
}
