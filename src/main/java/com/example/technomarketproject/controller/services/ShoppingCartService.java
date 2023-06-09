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
        Product p = productRepository.findById(dto.getProduct().getId()).get();
        User u = opt.get();
        ShoppingCart sc;
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
        SimpleProductDTO productDTO = mapper.map(p, SimpleProductDTO.class);
        SimpleShoppingCartDTO ssc = mapper.map(sc, SimpleShoppingCartDTO.class);
        ssc.setProductId(productDTO);
        logger.info("A user with ID " + id + " added a product with ID "
                + dto.getProduct().getId() + " with the quantity of " + dto.getQuantity());
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
        if(dto.getQuantity() > opt.get().getQuantity()) {
            throw new BadRequestException("Only " + opt.get().getQuantity() + " pieces of this product available in cart!");
        }
        opt.get().setQuantity(opt.get().getQuantity() - dto.getQuantity());
        if(opt.get().getQuantity() == 0){
            logger.info("A user with ID " + userId + " removed product with ID " + productId + " from his shopping cart.");
            shoppingCartRepository.delete(opt.get());
        }
        else{
            logger.info("A user with ID " + userId + " removed "
                    + dto.getQuantity() + " pieces of product with ID " + productId + " from his shopping cart.");
            shoppingCartRepository.save(opt.get());
        }
    }
    public List<SimpleShoppingCartDTO> showUserCart(int loggedId) {
        if(!userRepository.existsById(loggedId) || userRepository.findById(loggedId).get().isDeleted()){
            throw new FileNotFoundException("User with id " + loggedId + " does not exist!");
        }
        if(!userRepository.existsById(loggedId)){
            throw new FileNotFoundException("User with id " + loggedId + " does not exist!");
        }
        User u = userRepository.findById(loggedId).get();
        List<SimpleShoppingCartDTO> list = new ArrayList<>();
        for(ShoppingCart s : shoppingCartRepository.findAllByUser(u)){
            SimpleProductDTO dto = mapper.map(s.getProduct(), SimpleProductDTO.class);
            SimpleShoppingCartDTO ssc = new SimpleShoppingCartDTO();
            ssc.setProductId(dto);
            ssc.setQuantity(s.getQuantity());
            list.add(ssc);
        }
        return list;
    }
}
