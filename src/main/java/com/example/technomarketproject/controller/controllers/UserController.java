package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.UserService;
import com.example.technomarketproject.model.DTOs.ChangePasswordDTO;
import com.example.technomarketproject.model.DTOs.UserLoginDTO;
import com.example.technomarketproject.model.DTOs.UserRegisterDTO;
import com.example.technomarketproject.model.DTOs.UserWithoutPasswordDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends GeneralController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public UserWithoutPasswordDTO register(@RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody UserLoginDTO dto, HttpSession s){
        UserWithoutPasswordDTO user = userService.login(dto);
        s.setAttribute("LOGGED_ID", user.getId());
        return user;
    }

    @PostMapping("/users/{userId}/change-password")
    public UserWithoutPasswordDTO changePassword(@PathVariable int userId, HttpSession s, @RequestBody ChangePasswordDTO dto) {
        int loggedId = findSessionLoggedId(s);
        return userService.changePassword(userId, loggedId, dto);
    }
}
