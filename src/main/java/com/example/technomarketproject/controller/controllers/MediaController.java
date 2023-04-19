package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.MediaService;
import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class MediaController extends GeneralController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/product-image/{productId}")
    public SimpleProductDTO uploadProductImages(@RequestParam("file") List<MultipartFile> files, @PathVariable int productId, HttpSession s) {
        int loggedId = findSessionLoggedId(s);
        return mediaService.uploadProductImages(files, productId, loggedId);
    }

    @SneakyThrows
    @GetMapping("/product-image/{fileName}")
    public void downloadProductImage(@PathVariable String fileName, HttpServletResponse response) {
        File f = mediaService.downloadProductImage(fileName);
        Files.copy(f.toPath(), response.getOutputStream());
    }

    @DeleteMapping("/product-image/{fileName}")
    public void deleteProductImage(@PathVariable String fileName, HttpSession s) {
        int loggedId = findSessionLoggedId(s);
        mediaService.deleteProductImage(fileName, loggedId);
    }

}
