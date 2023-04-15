package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddReviewDTO;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.Review;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService extends AbstractService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(AddReviewDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        if (dto.getRating() <= 0 || dto.getRating() > 10) {
            throw new BadRequestException("Product rating should be between 1 and 10.");
        }
        Review r = mapper.map(dto, Review.class);
        r.setUser(opt.get());
        reviewRepository.save(r);
        return r;
    }

    public void deleteReview(int reviewId, int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optUser.isEmpty()){
            throw new FileNotFoundException("No user with id " + userId + " found!");
        }
        if(optReview.isEmpty()){
            throw new FileNotFoundException("No review with id " + reviewId + " found!");
        }
        reviewRepository.deleteById(reviewId);
    }

    public Review showReview(int reviewId) {
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optReview.isEmpty()){
            throw new FileNotFoundException("Review with id " + reviewId + " not found!");
        }
        return optReview.get();
    }

    public List<Review> showUserReviews(int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + userId + " not found!");
        }
        return opt.get().getReviews();
    }

    public List<Review> showProductReviews(int productId) {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        return opt.get().getReviews();
    }
}
