package com.yash.MovieRoger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yash.MovieRoger.dto.ShowDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "show_time", columnDefinition = "TIME", nullable = false)
    private LocalDateTime showTime;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_on")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Date updatedAt;

    @ManyToOne
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JsonIgnore
    private Theater theater;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowSeat> seats;

    public static List<ShowDTO> toResource(List<Show> show) {

        if (!CollectionUtils.isEmpty(show)) {
            return show.stream().map(Show::toResource).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public static ShowDTO toResource(Show show) {

        return ShowDTO.builder()
                .id(show.getId())
                .showTime(show.getShowTime())
                .movieId(show.getMovie().getId())
                .theaterId(show.getTheater().getId())
                .seats(ShowSeat.toResource(show.getSeats()))
                .createdAt(show.getCreatedAt())
                .updatedAt(show.getUpdatedAt())
                .movieDTO(Movie.toResource(show.getMovie()))
                .theaterDTO(Theater.toResource(show.getTheater()))
                .build();
    }

    public static Show toEntity(ShowDTO showResource) {

        return Show.builder()
                .showTime(showResource.getShowTime())
                .build();

    }
}
