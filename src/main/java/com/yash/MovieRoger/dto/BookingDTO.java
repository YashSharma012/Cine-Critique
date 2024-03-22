package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.SeatType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
//@ToString
public class BookingDTO {
    @NotEmpty(message = "SeatNumbers can't be empty")
    private Set<String> seatsNumbers;

    @Min(value = 1, message = "Invalid user ID")
    private long userId;

    @Min(value = 1, message = "Invalid show ID")
    private long showId;

    @NotNull(message = "Seat Type can't be null")
    private SeatType seatType;
}
