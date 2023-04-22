package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddReviewDTO;
import com.example.technomarketproject.model.DTOs.SimpleReviewDTO;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.Review;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService extends AbstractService {
    @Transactional
    public SimpleReviewDTO addReview(AddReviewDTO dto, int id) {
        Optional<Product> optP = productRepository.findById(dto.getProductId().getId());
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found");
        }
        if(optP.isEmpty()){
            throw new FileNotFoundException("Product with this id was not found");
        }
        if(reviewRepository.existsByUserAndProductId(opt.get(), optP.get())){
            throw new UnauthorizedException("The user already has a review for this product!");
        }

        Product p = productRepository.findById(dto.getProductId().getId()).get();

        double currentRating = p.getRating();
        currentRating = currentRating * p.getReviews().size();
        currentRating += dto.getRating();
        currentRating = currentRating / (p.getReviews().size() + 1);
        p.setRating(currentRating);

        productRepository.save(p);
        Review r = new Review();
        r.setUser(opt.get());
        r.setProductId(p);
        r.setRating(dto.getRating());
        r.setTitle(dto.getTitle());
        r.setComment(dto.getComment());
        reviewRepository.save(r);

        logger.info("A new review has been added for product with ID "
                + dto.getProductId().getId() + " by a user with ID " + id);

        return mapper.map(r, SimpleReviewDTO.class);
    }
    @Transactional
    public void deleteReview(int reviewId, int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optUser.isEmpty()){
            throw new FileNotFoundException("No user with id " + userId + " found!");
        }
        if(optReview.isEmpty()){
            throw new FileNotFoundException("No review with id " + reviewId + " found!");
        }

        Product p = optReview.get().getProductId();
        double currentRating = p.getRating();
        currentRating = currentRating * p.getReviews().size();
        currentRating -= optReview.get().getRating();
        currentRating = currentRating / (p.getReviews().size() - 1);
        p.setRating(currentRating);
        reviewRepository.delete(optReview.get());
        p.getReviews().remove(optReview.get());
        productRepository.save(p);

        logger.info("A user with ID " + userId + " deleted a review with ID "
                + reviewId + " for a product with ID " + p.getId());

    }

    public SimpleReviewDTO showReview(int reviewId) {
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optReview.isEmpty()){
            throw new FileNotFoundException("Review with id " + reviewId + " not found!");
        }
        Review r = optReview.get();
        SimpleReviewDTO dto = mapper.map(r, SimpleReviewDTO.class);
        return dto;
    }

    public List<SimpleReviewDTO> showUserReviews(int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty() || opt.get().isDeleted()){
            throw new FileNotFoundException("User with id " + userId + " not found!");
        }
        return opt.get().getReviews()
                .stream()
                .map(o -> mapper.map(o, SimpleReviewDTO.class))
                .collect(Collectors.toList());
    }

    public List<SimpleReviewDTO> showProductReviews(int productId) {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        return opt.get().getReviews()
                .stream()
                .map(o -> mapper.map(o , SimpleReviewDTO.class))
                .collect(Collectors.toList());
    }
}
