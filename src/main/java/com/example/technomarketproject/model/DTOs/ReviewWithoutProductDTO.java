package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWithoutProductDTO {

    private String title;
    private String comment;
    private double rating;
    private UserWithIdNameDTO user;

}
