package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.SearchHistory;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    Optional<List<SearchHistory>> findByUser(User u);
}
