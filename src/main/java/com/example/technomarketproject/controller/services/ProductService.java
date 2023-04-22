package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.*;
import com.example.technomarketproject.model.entities.*;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.CharacteristicRepository;
import com.example.technomarketproject.model.repositories.ProductCharacteristicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{
    @Autowired
    private ProductCharacteristicRepository productCharacteristicRepository;
    @Autowired
    private CharacteristicRepository characteristicRepository;
    // Adding a product also adds another product-characteristic relationship
    // That`s why we need Transactional
    @Transactional
    public SimpleProductDTO addProduct(AddProductDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        Optional<Subcategory> optS = subcategoryRepository.findById(dto.getSubcategoryId());
        if(optS.isEmpty()){
            throw new FileNotFoundException("Subcategory with this id does not exist");
        }
        if(productRepository.existsByName(dto.getName())){
            throw new BadRequestException("Product name must be unique!");
        }
        Product p = new Product();
        p.setSubcategory(optS.get());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());
        productRepository.save(p);

        List<ProductCharacteristic> list = new ArrayList<>();
        for(CharacteristicWithValuesDTO c : dto.getCharacteristics()){
            Optional<Characteristic> opt = characteristicRepository.findById(c.getId());
            if(opt.isEmpty()){
                throw new BadRequestException("No characteristic with id " + c.getId() + " found!");
            }
            Characteristic current = opt.get();
            ProductCharacteristicKey key = new ProductCharacteristicKey();
            key.setProductId(p.getId());
            key.setCharacteristicId(current.getId());
            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setProduct(p);
            pc.setCharacteristic(current);
            pc.setValue(c.getValue());
            pc.setId(key);
            list.add(pc);
        }
        productCharacteristicRepository.saveAll(list);
        return mapper.map(p, SimpleProductDTO.class);
    }
    public void removeProduct(int productId, int userId) {
        if(!findUserById(userId).isAdmin()){
            throw new UnauthorizedException("User must be admin!");
        }
        if(!productRepository.existsById(productId)){
            throw new FileNotFoundException("Product with this id not found!");
        }
        Product p = productRepository.findById(productId).get();
        productRepository.delete(p);
    }
    public SimpleProductDTO showSpecificProduct(int productId, int userId) {
        Optional<Product> optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty()){
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        if(userId != 0){
            User user = userRepository.findById(userId).get();
            SearchHistoryKey key = new SearchHistoryKey();
            key.setProductId(productId);
            key.setUserId(userId);
            SearchHistory current = new SearchHistory();

            if(searchHistoryRepository.findByUserAndProductId(user, optProduct.get()).isEmpty()){
                current.setId(key);
                current.setProductId(optProduct.get());
                current.setUser(user);
            }
            current.setLastSeen(LocalDateTime.now());
            searchHistoryRepository.save(current);
        }
        return mapper.map(optProduct.get(), SimpleProductDTO.class);
    }

    public Page<SimpleProductDTO> searchProductsByName(String productName, Pageable pageable) {
        Page<Product> products = productRepository.findAllByNameContainingIgnoreCase(productName, pageable);
        if(products.isEmpty()){
            throw new FileNotFoundException("No products found with name containing: " + productName);
        }

        return products.map(p -> mapper.map(p, SimpleProductDTO.class));
    }
    public Page<Product> filterProducts(ProductFilteringDTO filter, Pageable pageable) {
        String name = filter.getName();
        Integer subcategoryId = filter.getSubcategoryId();
        Integer categoryId = filter.getCategoryId();
        Integer ratingFrom = filter.getRatingFrom();
        Double priceFrom = filter.getPriceFrom();
        Double priceTo = filter.getPriceTo();
        String description = filter.getDescription();
        List<Integer> characteristicIds = filter.getCharacteristicIds();
        List<String> characteristicValues = filter.getCharacteristicValues();

        Page<Product> filteredProducts = productRepository.findByMultipleCharacteristics(
                name,
                subcategoryId,
                categoryId,
                ratingFrom,
                priceFrom,
                priceTo,
                description,
                characteristicIds,
                characteristicValues,
                pageable
        );

        return filteredProducts;
    }
}
