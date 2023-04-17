package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.entities.*;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class AbstractService {
    @Autowired
    protected ProductCharacteristicRepository productCharacteristicRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected SubcategoryRepository subcategoryRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected ShoppingCartRepository shoppingCartRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected SearchHistoryRepository searchHistoryRepository;

    protected User findUserById(int id){
        User u = userRepository.findById(id).orElseThrow(() -> new FileNotFoundException("User with id " + id + " not found!"));
        return u;
    }
}
