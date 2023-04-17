package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.CategoryService;
import com.example.technomarketproject.model.DTOs.AddCategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleCategoryDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController extends GeneralController{
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public SimpleCategoryDTO addCategory(@Valid @RequestBody AddCategoryDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return categoryService.addCategory(dto, id);
    }

    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(HttpSession s, @PathVariable int categoryId){
        int userId = findSessionLoggedId(s);
        categoryService.removeCategory(categoryId, userId);
    }

    @GetMapping("/categories/{categoryId}")
    public SimpleCategoryDTO showSpecificCategory(@PathVariable int categoryId){
        return categoryService.showCategory(categoryId);
    }

    @GetMapping("/categories")
    public List<SimpleCategoryDTO> showAllCategories(){
        return categoryService.showAll();
    }
}
