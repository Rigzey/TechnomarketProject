package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.SearchHistory;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    public List<SearchHistory> findByUser(User u);
}
