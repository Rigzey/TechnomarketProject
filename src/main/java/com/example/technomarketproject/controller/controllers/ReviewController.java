package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ReviewService;
import com.example.technomarketproject.model.DTOs.AddReviewDTO;
import com.example.technomarketproject.model.entities.Review;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController extends GeneralController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public Review add(@RequestBody AddReviewDTO dto, HttpSession s) {
        int id = findSessionLoggedId(s);
        return reviewService.addReview(dto, id);
    }

}
