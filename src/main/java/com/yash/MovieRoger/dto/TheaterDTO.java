package com.yash.MovieRoger.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TheaterDTO {
    private long id;

    private String name;

    private int regularSeats;

    private int reclinerSeats;

    private String city;

    private String address;

    private List<ShowDTO> shows;
}
