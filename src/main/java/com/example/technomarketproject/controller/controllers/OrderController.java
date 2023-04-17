package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.OrderService;
import com.example.technomarketproject.model.DTOs.AddOrderDTO;
import com.example.technomarketproject.model.DTOs.SimpleOrderDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController extends GeneralController{
    @Autowired
    private OrderService orderService;
    @PostMapping("/orders")
    public SimpleOrderDTO add(@Valid @RequestBody AddOrderDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return orderService.addOrder(dto, id);
    }
    @DeleteMapping("/orders/{orderId}")
    public void remove(@PathVariable int orderId, HttpSession s){
        int userId = findSessionLoggedId(s);
        orderService.removeOrder(orderId, userId);
    }

    @GetMapping("/orders/{orderId}")
    public SimpleOrderDTO showSpecific(@PathVariable int orderId, HttpSession s){
        int userId = findSessionLoggedId(s);
        return orderService.showSpecific(orderId, userId);
    }
    @GetMapping("/orders/user/{userId}")
    public List<SimpleOrderDTO> showUserOrders(@PathVariable int userId, HttpSession s){
        int sessionLoggedId = findSessionLoggedId(s);
        return orderService.showUserOrders(userId, sessionLoggedId);
    }
}
