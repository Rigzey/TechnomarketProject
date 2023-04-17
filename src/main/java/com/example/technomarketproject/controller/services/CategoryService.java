package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddCategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleCategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.Subcategory;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.ProductCharacteristicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService extends AbstractService {

    @Autowired
    private ProductCharacteristicRepository productCharacteristicRepository;

    public SimpleCategoryDTO addCategory(AddCategoryDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(categoryRepository.existsByName(dto.getName())){
            throw new BadRequestException("Category with this name already exists!");
        }
        Category category = mapper.map(dto, Category.class);
        categoryRepository.save(category);
        return mapper.map(category, SimpleCategoryDTO.class);
    }
    // Removing a category will remove all its subcategories
    // All the subcategories will remove their products, etc.
    // That is why we add Transactional
    @Transactional
    public void removeCategory(int categoryId, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(categoryRepository.findById(categoryId).isEmpty()){
            throw new FileNotFoundException("Category with id " + categoryId + " does not exist!");
        }
        Category c = categoryRepository.findById(categoryId).get();
        categoryRepository.delete(c);
    }

    public SimpleCategoryDTO showCategory(int categoryId) {
        Optional<Category> opt = categoryRepository.findById(categoryId);
        if (opt.isEmpty()) {
            throw new FileNotFoundException("Category with this id not found!");
        }
        Category c = opt.get();
        return mapper.map(c, SimpleCategoryDTO.class);
    }

    public List<SimpleCategoryDTO> showAll() {
        return categoryRepository.findAll()
                .stream()
                .map(o -> mapper.map(o, SimpleCategoryDTO.class))
                .collect(Collectors.toList());
    }
}
