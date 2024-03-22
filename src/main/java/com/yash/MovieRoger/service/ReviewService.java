package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.ReviewDTO;
import com.yash.MovieRoger.model.Movie;
import com.yash.MovieRoger.model.Review;
import com.yash.MovieRoger.repository.MovieRepository;
import com.yash.MovieRoger.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    public void addReview(ReviewDTO reviewDTO) {
        Review review = Review.toEntity(reviewDTO);
        Movie movie = movieRepository.findById(review.getMovie().getId()).orElse(null);

        reviewRepository.save(review);

        if(movie!=null) {
            Double average = reviewRepository.getReviewAverage(movie.getId());
            movie.setRating(average);
            movieRepository.save(movie);
        }
    }

    public ReviewDTO getReview(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) {
            throw new EntityNotFoundException("Review doesn't exist for id:" + id);
        }
        return Review.toResource(review.get());
    }
}
