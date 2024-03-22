package com.yash.MovieRoger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class ShowDTO {

    private long id;

    @NotNull(message = "Show Time is Mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime showTime;

    @NotNull(message = "Movie is mandatory for Show")
    private long movieId;

    @NotNull(message = "Theater is mandatory for Show")
    private long theaterId;

    private Date createdAt;

    private Date updatedAt;

    private MovieDTO movieDTO;

    private TheaterDTO theaterDTO;

    private List<ShowSeatDTO> seats;
}
