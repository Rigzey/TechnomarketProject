package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.NewCategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.NotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.CategoryRepository;
import com.example.technomarketproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    public Category add(NewCategoryDTO dto, int id) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id does not exist"));
        if(!u.isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        Category category = mapper.map(dto, Category.class);
        categoryRepository.save(category);
        return category;
    }
}
