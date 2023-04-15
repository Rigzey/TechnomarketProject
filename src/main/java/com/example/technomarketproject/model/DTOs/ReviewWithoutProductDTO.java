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
    protected int id;
    protected String title;
    protected String comment;
    protected double rating;
    protected UserWithoutPasswordDTO user;
}
