package com.example.technomarketproject.model.DTOs;

import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDTO {

    private String title;
    private String description;
    private LocalDate date;
    private User user;
    private Product product;
    private double rating;
}
