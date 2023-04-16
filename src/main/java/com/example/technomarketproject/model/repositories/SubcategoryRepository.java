package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    boolean existsByName(String name);

    void deleteAllByCategory(Category c);

    List<Subcategory> findAllByCategory(Category c);
}
