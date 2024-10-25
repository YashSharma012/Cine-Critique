package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.Genre;
import lombok.Builder;
import lombok.Data;
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
