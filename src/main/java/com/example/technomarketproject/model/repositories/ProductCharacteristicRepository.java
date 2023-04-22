package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.ProductCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharacteristicRepository extends JpaRepository<ProductCharacteristic, Integer> {
}
