package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.SeatType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ShowSeatDTO {
    private long id;

    private String seatNumber;

    private int rate;

    private SeatType seatType;

    private boolean booked;

    private Date bookedAt;
}
