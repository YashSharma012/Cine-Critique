package com.yash.MovieRoger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yash.MovieRoger.dto.MovieDTO;
import com.yash.MovieRoger.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Double rating; //Average rating of all reviews

    @OneToMany(mappedBy = "movie")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Show> showList = new ArrayList<>();

    public static Movie toEntity(MovieDTO movieDTO) {
        return Movie.builder()
                .title(movieDTO.getTitle())
                .genre(movieDTO.getGenre())
                .build();
    }

    public static MovieDTO toResource(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .reviews(Review.toResource(movie.getReviewList()))
                .build();
    }
}
