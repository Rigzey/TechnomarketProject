package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddSubcategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.Subcategory;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryService extends AbstractService{
    public Subcategory addSubcategory(AddSubcategoryDTO dto, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        Subcategory subcategory = new Subcategory();
        Optional<Category> opt = categoryRepository.findById(dto.getCategoryId());
        if(opt.isEmpty()){
            throw new FileNotFoundException("Category with this id not found!");
        }
        subcategory.setCategory(opt.get());
        subcategory.setName(dto.getName());
        subcategoryRepository.save(subcategory);
        return subcategory;
    }

    public void removeSubcategory(int id, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        subcategoryRepository.deleteById(id);
    }

    public Subcategory showSpecificSubcategory(int id) {
        Optional<Subcategory> opt = subcategoryRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("Subcategory with this id not found!");
        }
        return opt.get();
    }

    public List<Subcategory> showAllSubcategories() {
        return subcategoryRepository.findAll();
    }
}
