package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.controller.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController extends GeneralController {

    @Autowired
    private ReviewService reviewService;


}
