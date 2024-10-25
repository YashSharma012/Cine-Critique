package com.yash.MovieRoger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TheaterUpdateDTO {
    @NotBlank(message = "Theater ID is required")
    private long id;

    @NotBlank(message = "Please provide number of REGULAR seats")
    private int regularSeats;

    @NotBlank(message = "Please provide number of RECLINER seats")
    private int reclinerSeats;
}
