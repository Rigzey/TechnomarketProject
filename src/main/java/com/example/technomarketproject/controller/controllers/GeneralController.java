package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {
    protected int findSessionLoggedId(HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You must login!");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
}
