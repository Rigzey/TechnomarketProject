package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.CategoryService;
import com.example.technomarketproject.model.DTOs.NewCategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController extends GeneralController{
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public Category addCategory(NewCategoryDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return categoryService.addCategory(dto, id);
    }

    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId, HttpSession s){
        int userId = findSessionLoggedId(s);
        categoryService.removeCategory(categoryId, userId);
    }
}
