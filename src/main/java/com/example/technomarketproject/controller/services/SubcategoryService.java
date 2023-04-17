package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddSubcategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleSubcategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.Subcategory;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubcategoryService extends AbstractService{
    @Transactional
    public SimpleSubcategoryDTO addSubcategory(AddSubcategoryDTO dto, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(dto.getName().length() > 45 || dto.getName().isBlank()){
            throw new BadRequestException("Invalid name length!");
        }
        if(subcategoryRepository.existsByName(dto.getName())){
            throw new BadRequestException("Subcategory with this name already exists!");
        }
        Subcategory subcategory = new Subcategory();
        Optional<Category> opt = categoryRepository.findById(dto.getCategoryId());
        if(opt.isEmpty()){
            throw new FileNotFoundException("Category with this id not found!");
        }
        subcategory.setCategory(opt.get());
        subcategory.setName(dto.getName());
        subcategoryRepository.save(subcategory);
        return mapper.map(subcategory, SimpleSubcategoryDTO.class);
    }

    public void removeSubcategory(int id, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(!subcategoryRepository.existsById(id)){
            throw new FileNotFoundException("Subcategory with this id not found!");
        }
        Subcategory s = subcategoryRepository.findById(id).get();
        removeAllAboutSubcategory(s);
    }

    public SimpleSubcategoryDTO showSpecificSubcategory(int id) {
        Optional<Subcategory> opt = subcategoryRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("Subcategory with this id not found!");
        }
        return mapper.map(opt.get(), SimpleSubcategoryDTO.class);
    }

    public List<SimpleSubcategoryDTO> showAllSubcategories() {
        return subcategoryRepository.findAll()
                .stream()
                .map(o -> mapper.map(o, SimpleSubcategoryDTO.class))
                .collect(Collectors.toList());
    }
}
