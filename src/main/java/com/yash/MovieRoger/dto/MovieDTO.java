package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.Genre;
import com.yash.MovieRoger.model.Review;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Data
public class MovieDTO {
    private Long id;

    private String title;

    private Genre genre;

    private Double rating;

    private List<ReviewDTO> reviews;
}
