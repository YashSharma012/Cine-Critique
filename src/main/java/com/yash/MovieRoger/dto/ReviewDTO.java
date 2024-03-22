package com.yash.MovieRoger.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
    private String movieReview;

    private double rating;

    private Long movieId;
}
