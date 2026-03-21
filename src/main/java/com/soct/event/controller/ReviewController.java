/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.soct.event.model.Review;
import com.soct.event.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add review
    @PostMapping
    public Review addReview(@RequestBody Review review){
        return reviewService.addReview(review);
    }

    // Get reviews for event
    @GetMapping("/event/{eventId}")
    public List<Review> getReviews(@PathVariable String eventId){
        return reviewService.getReviewsByEvent(eventId);
    }
}
