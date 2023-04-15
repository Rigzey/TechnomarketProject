package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ReviewService;
import com.example.technomarketproject.model.DTOs.AddReviewDTO;
import com.example.technomarketproject.model.DTOs.SimpleReviewDTO;
import com.example.technomarketproject.model.entities.Review;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController extends GeneralController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public SimpleReviewDTO add(@RequestBody AddReviewDTO dto, HttpSession s) {
        int id = findSessionLoggedId(s);
        return reviewService.addReview(dto, id);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public void delete(@PathVariable int reviewId, HttpSession s) {
        int userId = findSessionLoggedId(s);
        reviewService.deleteReview(reviewId, userId);
    }

    @GetMapping("/reviews/{reviewId}")
    public SimpleReviewDTO showReview(@PathVariable int reviewId) {
        return reviewService.showReview(reviewId);
    }

    @GetMapping("/reviews/user/{userId}")
    public List<SimpleReviewDTO> showUserReviews(@PathVariable int userId) {
        return reviewService.showUserReviews(userId);
    }

    @GetMapping("/reviews/product/{productId}")
    public List<SimpleReviewDTO> showProductReviews(@PathVariable int productId) {
        return reviewService.showProductReviews(productId);
    }

}
