package com.yash.MovieRoger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yash.MovieRoger.dto.TheaterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "regular_seats", nullable = false)
    private int regularSeat;

    @Column(name = "recliner_seats", nullable = false)
    private int reclinerSeat;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Show> shows = new ArrayList<>();

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<TheaterSeats> seats = new ArrayList<>();

    public static Theater toEntity(TheaterDTO theaterResource) {

        return Theater.builder()
                .name(theaterResource.getName())
                .city(theaterResource.getCity())
                .address(theaterResource.getAddress())
                .reclinerSeat(theaterResource.getReclinerSeats())
                .regularSeat(theaterResource.getRegularSeats())
                .build();
    }

    public static TheaterDTO toResource(Theater theater) {

        return TheaterDTO.builder()
                .id(theater.getId())
                .name(theater.getName())
                .city(theater.getCity())
                .reclinerSeats(theater.reclinerSeat)
                .regularSeats(theater.regularSeat)
                .address(theater.getAddress())
                .build();
    }
}
