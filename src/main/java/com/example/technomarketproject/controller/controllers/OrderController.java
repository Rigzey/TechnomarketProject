package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.OrderService;
import com.example.technomarketproject.model.DTOs.AddOrderDTO;
import com.example.technomarketproject.model.entities.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController extends GeneralController{
    @Autowired
    private OrderService orderService;
    @PostMapping("/orders")
    public Order add(@RequestBody AddOrderDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return orderService.addOrder(dto, id);
    }
    @DeleteMapping("/orders/{orderId}")
    public void remove(@PathVariable int orderId, HttpSession s){
        int userId = findSessionLoggedId(s);
        orderService.removeOrder(orderId, userId);
    }

    @GetMapping("/orders/{orderId}")
    public Order showSpecific(@PathVariable int orderId, HttpSession s){
        int userId = findSessionLoggedId(s);
        return orderService.showSpecific(orderId, userId);
    }
    @GetMapping("/orders/user/{userId}")
    public List<Order> showUserOrders(@PathVariable int userId, HttpSession s){
        int sessionLoggedId = findSessionLoggedId(s);
        return orderService.showUserOrders(userId, sessionLoggedId);
    }
}