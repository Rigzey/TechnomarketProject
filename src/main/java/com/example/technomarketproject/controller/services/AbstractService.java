package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.NotFoundException;
import com.example.technomarketproject.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    protected User findUserById(int id){
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return u;
    }
}
