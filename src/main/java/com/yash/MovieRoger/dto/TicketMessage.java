package com.yash.MovieRoger.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketMessage {
    private String userName;
    private String mobile;
    private String email;
    private ShowDTO show;
    private List<ShowSeatDTO> seats;
}
