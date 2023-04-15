package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {
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
    protected CategoryRepository categoryRepository;
    @Autowired
    protected SearchHistoryRepository searchHistoryRepository;

    protected User findUserById(int id){
        User u = userRepository.findById(id).orElseThrow(() -> new FileNotFoundException("User with id " + id + " not found!"));
        return u;
    }
}
