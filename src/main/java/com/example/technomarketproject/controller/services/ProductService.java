package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddProductDTO;
import com.example.technomarketproject.model.DTOs.CharacteristicsWithValuesDTO;
import com.example.technomarketproject.model.entities.Characteristic;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ProductCharacteristic;
import com.example.technomarketproject.model.entities.Subcategory;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.CharacteristicRepository;
import com.example.technomarketproject.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService extends AbstractService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;
    public Product addProduct(AddProductDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        Optional<Subcategory> optS = subcategoryRepository.findById(dto.getSubcategoryId());
        if(optS.isEmpty()){
            throw new FileNotFoundException("Subcategory with this id does not exist");
        }
        if(dto.getPrice() <= 0){
            throw new BadRequestException("Price cannot be less than 0!");
        }
        if(productRepository.existsByName(dto.getName())){
            throw new BadRequestException("Product name must be unique!");
        }
        if(dto.getDescription().length() > 200){
            throw new BadRequestException("Description length cannot be more than 200!");
        }
        if(dto.getDescription().length() == 0){
            throw new BadRequestException("Description cannot be null!");
        }
        Product p = new Product();
        p.setSubcategory(optS.get());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());

        for(CharacteristicsWithValuesDTO c : dto.getCharacteristics()){
            Optional<Characteristic> opt = characteristicRepository.findById(c.getId());
            if(opt.isEmpty()){
                throw new BadRequestException("No characteristic with id " + c.getId() + " found!");
            }
            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setProduct(p);
            pc.setCharacteristic(opt.get());
            pc.setValue(c.getValue());
        }
        productRepository.save(p);
        return p;
    }

    public void removeProduct(int productId, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        productRepository.deleteById(productId);
    }

    public Product showSpecificProduct(int productId) {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        return opt.get();
    }
}
