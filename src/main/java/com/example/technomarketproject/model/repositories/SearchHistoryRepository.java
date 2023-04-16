package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.SearchHistory;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    Optional<List<SearchHistory>> findByUser(User u);
}
