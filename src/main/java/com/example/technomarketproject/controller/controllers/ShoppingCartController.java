package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController extends GeneralController {

    @Autowired
    private ShoppingCartService shoppingCartService;
}
