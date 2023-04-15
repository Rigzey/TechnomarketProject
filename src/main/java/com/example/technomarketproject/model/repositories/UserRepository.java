package com.example.technomarketproject.model.repositories;

import com.example.technomarketproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<User> findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
