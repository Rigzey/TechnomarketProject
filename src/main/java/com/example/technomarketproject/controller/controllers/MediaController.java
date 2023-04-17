package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.MediaService;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import com.example.technomarketproject.model.entities.Product;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class MediaController extends GeneralController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/product-image/{productId}")
    public SimpleProductDTO upload(@RequestParam("file") MultipartFile file, @PathVariable int productId, HttpSession s) {
        int loggedId = findSessionLoggedId(s);
        return mediaService.upload(file, productId, loggedId);
    }

    @SneakyThrows
    @GetMapping("/product-image/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        File f = mediaService.download(fileName);
        Files.copy(f.toPath(), response.getOutputStream());
    }

    @DeleteMapping("/product-image/{fileName}")
    public void delete(@PathVariable String fileName, HttpSession s) {
        int loggedId = findSessionLoggedId(s);
        mediaService.delete(fileName, loggedId);
    }

}
