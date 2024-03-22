package com.yash.MovieRoger.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TicketDTO {

    private long id;

    private String allottedSeats;

    private double amount;

    private Date bookedAt;

    private ShowDTO show;
}
