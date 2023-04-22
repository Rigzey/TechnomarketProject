package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddSubcategoryDTO;
import com.example.technomarketproject.model.DTOs.SimpleSubcategoryDTO;
import com.example.technomarketproject.model.entities.Category;
import com.example.technomarketproject.model.entities.Subcategory;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubcategoryService extends AbstractService{
    public SimpleSubcategoryDTO addSubcategory(AddSubcategoryDTO dto, int userId) {
        if(!findUserById(userId).isAdmin()){
            logger.error("A non-admin user with ID " + userId + " tried to add a new subcategory.");
            throw new UnauthorizedException("User must be admin!");
        }
        if(subcategoryRepository.existsByName(dto.getName())){
            logger.error("A user with ID " + userId + " tried to add an existing subcategory with name " + dto.getName());
            throw new BadRequestException("Subcategory with this name already exists!");
        }
        Subcategory subcategory = new Subcategory();
        Optional<Category> opt = categoryRepository.findById(dto.getCategoryId());
        if(opt.isEmpty()){
            logger.error("A user with ID " + userId + " tried to add a subcategory with name " + dto.getName() +
                    " to a non-existing category with ID " + dto.getCategoryId());
            throw new FileNotFoundException("Category with this id not found!");
        }
        subcategory.setCategory(opt.get());
        subcategory.setName(dto.getName());
        subcategoryRepository.save(subcategory);
        logger.info("A new subcategory with name " + dto.getName() + " has been created in category " + dto.getCategoryId());
        return mapper.map(subcategory, SimpleSubcategoryDTO.class);
    }
    public void removeSubcategory(int id, int userId) {
        if(!findUserById(userId).isAdmin()){
            logger.error("A non-admin user with ID " + userId + " tried to remove a subcategory with ID " + id);
            throw new UnauthorizedException("User must be admin!");
        }
        if(!subcategoryRepository.existsById(id)){
            logger.error("A user with ID " + userId + " tried to remove a non-existing subcategory with ID " + id);
            throw new FileNotFoundException("Subcategory with this id not found!");
        }
        Subcategory s = subcategoryRepository.findById(id).get();
        subcategoryRepository.delete(s);
        logger.info("A user with ID " + userId + " has successfully removed a subcategory with ID " + id);
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
