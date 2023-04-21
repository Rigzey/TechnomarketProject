package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ProductService;
import com.example.technomarketproject.model.DTOs.AddProductDTO;
import com.example.technomarketproject.model.DTOs.ProductFilteringDTO;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import com.example.technomarketproject.model.entities.Product;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<SimpleProductDTO>> searchProductsByName(
            @PathVariable String productName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SimpleProductDTO> productPage = productService.searchProductsByName(productName, pageable);

        List<SimpleProductDTO> productDTOs = productPage.getContent()
                .stream()
                .map(p -> mapper.map(p, SimpleProductDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new PageImpl<>(productDTOs, pageable, productPage.getTotalElements()));
    }

    @PostMapping("/products/filter")
    public Page<SimpleProductDTO> filterProducts(
            @RequestBody ProductFilteringDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Product> filteredProducts = productService.filterProducts(filter, PageRequest.of(page, size));
        List<SimpleProductDTO> filteredProductsDTOs = filteredProducts.stream()
                .map(product -> mapper.map(product, SimpleProductDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredProductsDTOs, filteredProducts.getPageable(), filteredProducts.getTotalElements());
    }
}
