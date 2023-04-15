package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ShoppingCartService;
import com.example.technomarketproject.model.DTOs.AddToShoppingCartDTO;
import com.example.technomarketproject.model.entities.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController extends GeneralController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/cart")
    private ShoppingCart addProduct(@RequestBody AddToShoppingCartDTO dto, HttpSession s) {
        int id = findSessionLoggedId(s);
        return shoppingCartService.addProduct(dto, id);
    }
}
