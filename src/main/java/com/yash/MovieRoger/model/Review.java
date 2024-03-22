package com.yash.MovieRoger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yash.MovieRoger.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movieReview;

    private double rating;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    private Movie movie;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    public static Review toEntity(ReviewDTO reviewDTO) {
        return Review.builder()
                .movieReview(reviewDTO.getMovieReview())
                .rating(reviewDTO.getRating())
                .movie(Movie.builder().id(reviewDTO.getMovieId()).build())
                .build();
    }

    public static ReviewDTO toResource(Review review) {
        return ReviewDTO.builder()
                .movieReview(review.getMovieReview())
                .rating(review.getRating())
                .movieId(review.getMovie().getId())
                .build();
    }

    public static List<ReviewDTO> toResource(List<Review> reviews) {
        if(CollectionUtils.isEmpty(reviews)) {
            return new ArrayList<>();
        }else {
            return reviews.stream().map(Review::toResource).collect(Collectors.toList());
        }
    }
}
