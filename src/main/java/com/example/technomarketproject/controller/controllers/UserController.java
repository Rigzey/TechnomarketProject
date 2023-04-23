package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.model.DTOs.*;
import com.example.technomarketproject.controller.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController extends GeneralController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public UserWithoutPasswordDTO register(@Valid @RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody UserLoginDTO dto, HttpSession s){
        UserWithoutPasswordDTO user = userService.login(dto);
        s.setAttribute("LOGGED_ID", user.getId());
        return user;
    }

    @GetMapping("/users/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @PostMapping("/users/{userId}/change-password")
    public UserWithoutPasswordDTO changePassword(@PathVariable int userId, HttpSession s, @Valid @RequestBody ChangePasswordDTO dto) {
        int loggedId = findSessionLoggedId(s);
        return userService.changePassword(userId, loggedId, dto);
    }

    @PostMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId, HttpSession s, @RequestBody UserWithPassDTO dto) {
        int loggedId = findSessionLoggedId(s);
        userService.deleteUser(userId, loggedId, dto);
    }

    @PutMapping("/users/{userId}")
    public UserWithoutPasswordDTO updateUser(@PathVariable int userId, HttpSession s, @Valid @RequestBody UserWithoutPasswordDTO dto) {
        int loggedId = findSessionLoggedId(s);
        return userService.updateUser(userId, loggedId, dto);
    }

    @GetMapping("/users/{userId}")
    public UserWithoutPasswordDTO viewProfile(@PathVariable int userId) {
        return userService.viewProfile(userId);
    }

    @GetMapping("/users/{userId}/history")
    public Set<ProductWithIdOnlyDTO> viewSearchHistory(@PathVariable int userId, HttpSession s) {
        int loggedId = findSessionLoggedId(s);
        return userService.viewSearchHistory(userId, loggedId);
    }
    @PostMapping("/users/favourites")
    public void addDeleteFavourites(@RequestBody ProductWithIdOnlyDTO dto, HttpSession s){
        int userId = findSessionLoggedId(s);
        userService.addRemoveFavourites(dto, userId);
    }
    @GetMapping("/users/favourites")
    public UserFavouritesDTO showUserFavourites(HttpSession s){
        int userId = findSessionLoggedId(s);
        return userService.showUserFavourites(userId);
    }
    @PostMapping("/users/forgot-password")
    public void forgotPass(@RequestBody UserEmailDTO dto){
        userService.forgotPass(dto);
    }
    @GetMapping("/reset-password")
    public UserWithoutPasswordDTO resetPass(@RequestParam String token, @RequestBody() String newPassword){
        return userService.resetPassword(token, newPassword);
    }
}
