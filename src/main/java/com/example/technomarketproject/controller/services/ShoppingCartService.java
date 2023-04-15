package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddToShoppingCartDTO;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ShoppingCart;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService extends AbstractService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart addProduct(AddToShoppingCartDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        ShoppingCart sc = mapper.map(dto, ShoppingCart.class);
        sc.setUser(opt.get());
        shoppingCartRepository.save(sc);
        return sc;
    }

    public void deleteProduct(int userId, int productId, int quantity) {
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Product> optProduct = productRepository.findById(productId);
        if(optUser.isEmpty()){
            throw new FileNotFoundException("User with id " + userId + " not found!");
        }
        if(optProduct.isEmpty()){
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        Optional<ShoppingCart> opt = shoppingCartRepository.findShoppingCartByUserAndProduct(optUser.get(), optProduct.get());
        if(opt.isEmpty()){
            throw new FileNotFoundException("Shopping cart does not exist!");
        }
        if(opt.get().getQuantity() < quantity){
            throw new BadRequestException("The quantity of a product cannot be negative!");
        }
        opt.get().setQuantity(opt.get().getQuantity() - quantity);
        if(opt.get().getQuantity() == 0){
            shoppingCartRepository.delete(opt.get());
        }
        else{
            shoppingCartRepository.save(opt.get());
        }
    }
}
