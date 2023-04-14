package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddReviewDTO;
import com.example.technomarketproject.model.entities.Review;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
