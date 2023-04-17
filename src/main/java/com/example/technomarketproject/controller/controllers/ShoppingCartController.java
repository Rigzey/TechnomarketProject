package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ShoppingCartService;
import com.example.technomarketproject.model.DTOs.AddToShoppingCartDTO;
import com.example.technomarketproject.model.DTOs.RemoveFromCartDTO;
import com.example.technomarketproject.model.DTOs.SimpleShoppingCartDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShoppingCartController extends GeneralController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/cart")
    private SimpleShoppingCartDTO addProduct(@Valid @RequestBody AddToShoppingCartDTO dto, HttpSession s) {
        int id = findSessionLoggedId(s);
        return shoppingCartService.addProduct(dto, id);
    }

    @PostMapping("/cart/{productId}")
    private void deleteProduct(@PathVariable int productId, HttpSession s, @Valid @RequestBody RemoveFromCartDTO dto) {
        int userId = findSessionLoggedId(s);
        shoppingCartService.deleteProduct(userId, productId, dto);
    }
    @GetMapping("/cart/user/{userId}")
    private List<SimpleShoppingCartDTO> showUserCart(@PathVariable int userId, HttpSession s){
        int loggedId = findSessionLoggedId(s);
        return shoppingCartService.showUserCart(userId, loggedId);
    }

}
