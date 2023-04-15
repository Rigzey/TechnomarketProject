package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddToShoppingCartDTO;
import com.example.technomarketproject.model.DTOs.SimpleShoppingCartDTO;
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

    public SimpleShoppingCartDTO addProduct(AddToShoppingCartDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        if(productRepository.findById(dto.getProduct().getId()).isEmpty()){
            throw new FileNotFoundException("Product with this id does not exist!");
        }
        if(dto.getQuantity() <= 0){
            throw new BadRequestException("Quantity must be positive!");
        }
        User u = opt.get();
        ShoppingCart sc = mapper.map(dto, ShoppingCart.class);
        sc.setUser(u);
        shoppingCartRepository.save(sc);
        return mapper.map(sc, SimpleShoppingCartDTO.class);
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
        Optional<ShoppingCart> opt = shoppingCartRepository.findShoppingCartByUserAndProductId(optUser.get(), optProduct.get());
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
