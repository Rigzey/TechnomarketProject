package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.model.DTOs.ErrorDTO;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class GeneralController {
    @Autowired
    protected ModelMapper mapper;
    protected int findSessionLoggedId(HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You must login!");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
}
