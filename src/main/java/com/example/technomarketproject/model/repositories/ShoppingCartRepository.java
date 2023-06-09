package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ShoppingCart;
import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findShoppingCartByUserAndProduct(User u, Product p);

    List<ShoppingCart> findAllByUser(User u);
    boolean existsByUserAndProduct(User u, Product p);
    ShoppingCart findByUserAndProduct(User u, Product p);

    void deleteAllByUser(User u);
}
