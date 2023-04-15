package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.*;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ShoppingCart;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        Product p = productRepository.findById(dto.getProduct().getId()).get();
        User u = opt.get();
        ShoppingCart sc = mapper.map(dto, ShoppingCart.class);
        sc.setProduct(p);
        sc.setUser(u);
        shoppingCartRepository.save(sc);
        SimpleShoppingCartDTO ssc = mapper.map(sc, SimpleShoppingCartDTO.class);
        ssc.setProductId(mapper.map(p, SimpleProductDTO.class));
        return ssc;
    }

    public void deleteProduct(int userId, int productId, RemoveFromCartDTO dto) {
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
        if(opt.get().getQuantity() < dto.getQuantity()){
            throw new BadRequestException("The quantity of a product cannot be negative!");
        }
        opt.get().setQuantity(opt.get().getQuantity() - dto.getQuantity());
        if(opt.get().getQuantity() == 0){
            shoppingCartRepository.delete(opt.get());
        }
        else{
            shoppingCartRepository.save(opt.get());
        }
    }

    public List<SimpleShoppingCartDTO> showUserCart(int userId, int loggedId) {
        if(userId != loggedId && !findUserById(loggedId).isAdmin()){
            throw new UnauthorizedException("Only admins can watch other users` cart!");
        }
        if(!userRepository.existsById(userId)){
            throw new FileNotFoundException("User with id " + userId + " does not exist!");
        }
        if(!userRepository.existsById(loggedId)){
            throw new FileNotFoundException("User with id " + loggedId + " does not exist!");
        }
        User u = userRepository.findById(userId).get();
        List<SimpleShoppingCartDTO> list = new ArrayList<>();
        for(ShoppingCart s : shoppingCartRepository.findAllByUser(u)){
            SimpleShoppingCartDTO ssc = new SimpleShoppingCartDTO();
            ssc.setProductId(mapper.map(s.getProduct(), SimpleProductDTO.class));
            ssc.setQuantity(s.getQuantity());
            list.add(ssc);
        }
        return list;
    }
}
