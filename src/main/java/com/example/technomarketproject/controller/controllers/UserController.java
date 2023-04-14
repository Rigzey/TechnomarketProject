package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
}
