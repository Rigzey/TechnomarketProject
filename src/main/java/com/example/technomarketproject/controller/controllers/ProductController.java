package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ProductService;
import com.example.technomarketproject.model.DTOs.AddProductDTO;
import com.example.technomarketproject.model.entities.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends GeneralController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public Product add(@RequestBody AddProductDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return productService.addProduct(dto, id);
    }
}
