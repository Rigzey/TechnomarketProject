package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.SubcategoryService;
import com.example.technomarketproject.model.DTOs.AddSubcategoryDTO;
import com.example.technomarketproject.model.entities.Subcategory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubcategoryController extends GeneralController{
    @Autowired
    private SubcategoryService subcategoryService;

    @PostMapping("/subcategories")
    public Subcategory add(@RequestBody AddSubcategoryDTO dto, HttpSession s){
        int userId = findSessionLoggedId(s);
        return subcategoryService.addSubcategory(dto, userId);
    }
    @DeleteMapping("/subcategories/{id}")
    public void delete(@PathVariable int id, HttpSession s){
        int userId = findSessionLoggedId(s);
        subcategoryService.removeSubcategory(id, userId);
    }
    @GetMapping("/subcategories/{id}")
    public Subcategory showSpecific(@PathVariable int id){
        return subcategoryService.showSpecificSubcategory(id);
    }
    @GetMapping("/subcategories")
    public List<Subcategory> showAll(){
        return subcategoryService.showAllSubcategories();
    }
}
