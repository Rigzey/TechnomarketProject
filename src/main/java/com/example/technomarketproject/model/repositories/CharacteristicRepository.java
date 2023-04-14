package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer> {
}
