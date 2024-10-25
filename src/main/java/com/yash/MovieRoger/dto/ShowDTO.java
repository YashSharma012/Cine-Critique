package com.yash.MovieRoger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class ShowDTO {

    private long id;

    @NotNull(message = "Show Time is Mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime showTime;

    @NotNull(message = "Movie is mandatory for Show")
    private long movieId;

    @NotNull(message = "Theater is mandatory for Show")
    private long theaterId;

    @NotNull(message = "Recliner seat price is mandatory")
    private int reclinerSeatPrice;

    @NotNull(message = "Regular seat price is mandatory for Show")
    private int regularSeatPrice;

    private Date createdAt;

    private Date updatedAt;

    private MovieDTO movieDTO;

    private TheaterDTO theaterDTO;

    private List<ShowSeatDTO> seats;
}
