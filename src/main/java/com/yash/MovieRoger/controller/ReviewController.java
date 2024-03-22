package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.ReviewDTO;
import com.yash.MovieRoger.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/add")
    public void addReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.addReview(reviewDTO);
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable("id") Long id) {
        return reviewService.getReview(id);
    }
}
