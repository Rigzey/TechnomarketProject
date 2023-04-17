package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.DiscountService;
import com.example.technomarketproject.model.DTOs.AddDiscountDTO;
import com.example.technomarketproject.model.DTOs.NewDiscountDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController extends GeneralController {
    @Autowired
    private DiscountService discountService;

    @PostMapping("/discounts")
    public NewDiscountDTO addDiscount(@Valid @RequestBody AddDiscountDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return discountService.addDiscount(dto, id);
    }
    @PostMapping("/discounts/{productId}")
    public void removeDiscount(@PathVariable int productId, HttpSession s){
        int loggedId = findSessionLoggedId(s);
        discountService.removeDiscount(productId, loggedId);
    }
}
