package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.CategoryService;
import com.example.technomarketproject.model.DTOs.NewCategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController{
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public Category addCategory(NewCategoryDTO dto, HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You must login!");
        }
        int id = (int) s.getAttribute("LOGGED_ID");
        return categoryService.add(dto, id);
    }
}
