package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ProductService;
import com.example.technomarketproject.model.DTOs.AddProductDTO;
import com.example.technomarketproject.model.DTOs.ProductFilteringDTO;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import com.example.technomarketproject.model.entities.Product;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController extends GeneralController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public SimpleProductDTO add(@Valid @RequestBody AddProductDTO dto, HttpSession s){
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

    @GetMapping("/products/search/{productName}")
    public List<SimpleProductDTO> searchProductsByName(@PathVariable String productName) {
        return productService.searchProductsByName(productName);
    }

    @PostMapping("/products/filter")
    public List<SimpleProductDTO> filterProducts(@RequestBody ProductFilteringDTO filter) {
        List<Product> filteredProducts = productService.filterProducts(filter);
        List<SimpleProductDTO> filteredProductsDTOs = filteredProducts.stream()
                .map(product -> mapper.map(product, SimpleProductDTO.class))
                .collect(Collectors.toList());
        return filteredProductsDTOs;
    }
}
