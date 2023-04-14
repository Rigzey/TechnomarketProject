package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddCategoryDTO;
import com.example.technomarketproject.model.DTOs.CategoryWithNameIdOnlyDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends AbstractService {

    public Category addCategory(AddCategoryDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(dto.getName().isBlank()){
            throw new BadRequestException("Category name cannot be null!");
        }
        if(categoryRepository.existsByName(dto.getName())){
            throw new BadRequestException("Category with this name already exists!");
        }
        Category category = mapper.map(dto, Category.class);
        categoryRepository.save(category);
        return category;
    }

    public void removeCategory(int categoryId, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        categoryRepository.deleteById(categoryId);
    }

    public Category showCategory(int categoryId) {
        Optional<Category> opt = categoryRepository.findById(categoryId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new FileNotFoundException("Category with this id not found!");
    }

    public List<Category> showAll() {
        return categoryRepository.findAll();
    }
}
