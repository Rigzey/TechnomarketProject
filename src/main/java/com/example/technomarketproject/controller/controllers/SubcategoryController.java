package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.SubcategoryService;
import com.example.technomarketproject.model.DTOs.AddSubcategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleSubcategoryDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubcategoryController extends GeneralController{
    @Autowired
    private SubcategoryService subcategoryService;

    @PostMapping("/subcategories")
    public SimpleSubcategoryDTO add(@RequestBody AddSubcategoryDTO dto, HttpSession s){
        int userId = findSessionLoggedId(s);
        return subcategoryService.addSubcategory(dto, userId);
    }
    @DeleteMapping("/subcategories/{id}")
    public void delete(@PathVariable int id, HttpSession s){
        int userId = findSessionLoggedId(s);
        subcategoryService.removeSubcategory(id, userId);
    }
    @GetMapping("/subcategories/{id}")
    public SimpleSubcategoryDTO showSpecific(@PathVariable int id){
        return subcategoryService.showSpecificSubcategory(id);
    }
    @GetMapping("/subcategories")
    public List<SimpleSubcategoryDTO> showAll(){
        return subcategoryService.showAllSubcategories();
    }
}
