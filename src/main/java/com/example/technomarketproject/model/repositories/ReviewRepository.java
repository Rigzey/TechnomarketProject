package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.Review;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByUser(User u);

    boolean existsByUserAndProductId(User user, Product p);

}
