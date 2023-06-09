package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Order;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User u);
}
