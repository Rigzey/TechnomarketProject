package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddCategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleCategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService extends AbstractService {

    public SimpleCategoryDTO addCategory(AddCategoryDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            logger.error("A non-admin user with ID " + id + " tried to add a new category.");
            throw new UnauthorizedException("User must be admin!");
        }
        if(categoryRepository.existsByName(dto.getName())){
            logger.error("A user with ID " + id + " tried to add an existing category with name " + dto.getName());
            throw new BadRequestException("Category with this name already exists!");
        }
        Category category = mapper.map(dto, Category.class);
        categoryRepository.save(category);
        logger.info("A new category with name " + dto.getName() + " has been created.");
        return mapper.map(category, SimpleCategoryDTO.class);
    }
    public void removeCategory(int categoryId, int userId) {
        if(!findUserById(userId).isAdmin()){
            logger.error("A non-admin user with ID " + userId + " tried to remove a category with ID " + categoryId);
            throw new UnauthorizedException("User must be admin!");
        }
        if(categoryRepository.findById(categoryId).isEmpty()){
            logger.error("A user with ID " + userId + " tried to remove a non-existing category with ID " + categoryId);
            throw new FileNotFoundException("Category with id " + categoryId + " does not exist!");
        }
        Category c = categoryRepository.findById(categoryId).get();
        categoryRepository.delete(c);
        logger.info("A user with ID " + userId + " has successfully removed a category with ID " + categoryId);
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
