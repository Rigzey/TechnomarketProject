package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.entities.*;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {
    @Autowired
    protected ProductCharacteristicRepository productCharacteristicRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected SubcategoryRepository subcategoryRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected ShoppingCartRepository shoppingCartRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected SearchHistoryRepository searchHistoryRepository;

    protected User findUserById(int id){
        User u = userRepository.findById(id).orElseThrow(() -> new FileNotFoundException("User with id " + id + " not found!"));
        return u;
    }
    @Transactional
    protected void removeAllAboutProduct(Product p){
        for(ProductCharacteristic pc : productCharacteristicRepository.findAll()){
            if(pc.getProduct() == p){
                productCharacteristicRepository.delete(pc);
            }
        }
        for(Order o : orderRepository.findAll()){
            if(o.getProducts().contains(p)){
                o.getProducts().remove(p);
                orderRepository.save(o);
            }
        }
        for(Review r : reviewRepository.findAll()){
            if(r.getProductId() == p){
                reviewRepository.delete(r);
            }
        }
        for(SearchHistory sh : searchHistoryRepository.findAll()){
            if(sh.getProductId() == p){
                searchHistoryRepository.delete(sh);
            }
        }
        for(ShoppingCart sc : shoppingCartRepository.findAll()){
            if(sc.getProduct() == p){
                shoppingCartRepository.delete(sc);
            }
        }
        for(User u : userRepository.findAll()){
            if(u.getFavourites().contains(p)){
                u.getFavourites().remove(p);
                userRepository.save(u);
            }
        }
        productRepository.delete(p);
    }
    @Transactional
    protected void removeAllAboutSubcategory(Subcategory s){
        for(Product p : productRepository.findAllBySubcategory(s).get()){
            removeAllAboutProduct(p);
        }
        subcategoryRepository.delete(s);
    }
}
