package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddToShoppingCartDTO;
import com.example.technomarketproject.model.DTOs.RemoveFromCartDTO;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import com.example.technomarketproject.model.DTOs.SimpleShoppingCartDTO;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ShoppingCart;
import com.example.technomarketproject.model.entities.ShoppingCartKey;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.ShoppingCartRepository;
import jakarta.transaction.Transactional;
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
        Product p = productRepository.findById(dto.getProduct().getId()).get();
        User u = opt.get();
        ShoppingCart sc = new ShoppingCart();
        if(shoppingCartRepository.existsByUserAndProduct(u, p)){
            sc = shoppingCartRepository.findByUserAndProduct(u, p);
            sc.setQuantity(sc.getQuantity() + dto.getQuantity());
            shoppingCartRepository.save(sc);
        }
        else{
            ShoppingCartKey key = new ShoppingCartKey();
            key.setProductId(p.getId());
            key.setUserId(u.getId());
            sc = mapper.map(dto, ShoppingCart.class);
            sc.setProduct(p);
            sc.setUser(u);
            sc.setId(key);
            shoppingCartRepository.save(sc);
        }
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
        opt.get().setQuantity(opt.get().getQuantity() - dto.getQuantity());
        if(opt.get().getQuantity() == 0){
            shoppingCartRepository.delete(opt.get());
        }
        else{
            shoppingCartRepository.save(opt.get());
        }
    }
    public List<SimpleShoppingCartDTO> showUserCart(int userId, int loggedId) {
        if(userId != loggedId){
            throw new UnauthorizedException("Cannot watch other users` cart");
        }
        if(!userRepository.existsById(userId) || userRepository.findById(userId).get().isDeleted()){
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
