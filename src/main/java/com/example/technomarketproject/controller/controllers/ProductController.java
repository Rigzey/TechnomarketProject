package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ProductService;
import com.example.technomarketproject.model.DTOs.AddProductDTO;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController extends GeneralController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public SimpleProductDTO add(@RequestBody AddProductDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return productService.addProduct(dto, id);
    }

    @DeleteMapping("/products/{productId}")
    public void delete(@PathVariable int productId, HttpSession s){
        int userId = findSessionLoggedId(s);
        productService.removeProduct(productId, userId);
    }

    @GetMapping("/products/{productId}")
    public SimpleProductDTO showSpecific(@PathVariable int productId, HttpSession s){
        int userId = 0;
        if(s.getAttribute("LOGGED_ID") != null){
            userId = (int) s.getAttribute("LOGGED_ID");
        }
        return productService.showSpecificProduct(productId, userId);
    }
}
