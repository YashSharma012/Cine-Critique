package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.TheaterDTO;
import com.yash.MovieRoger.enums.SeatType;
import com.yash.MovieRoger.model.Theater;
import com.yash.MovieRoger.model.TheaterSeats;
import com.yash.MovieRoger.repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    private static final Logger log = LoggerFactory.getLogger(TheaterService.class);
    @Autowired
    TheaterRepository theaterRepository;

    public TheaterDTO addTheater(TheaterDTO theaterDTO) {
        Theater theater = Theater.toEntity(theaterDTO);

        theater.getSeats().addAll(getTheaterSeats(theaterDTO.getRegularSeats(), theaterDTO.getReclinerSeats()));

        for(TheaterSeats theaterSeatEntity: theater.getSeats()) {
            theaterSeatEntity.setTheater(theater);
        }

        theater = theaterRepository.save(theater);

        log.info("Added new User [id: {}, name: {}", theater.getId(), theater.getName());

        return Theater.toResource(theater);
    }

    private List<TheaterSeats> getTheaterSeats(int regularSeats, int reclinerSeats) {
        List<TheaterSeats> theaterSeats = new ArrayList<>();

        //Recliner
        for(int i = 1; i <= reclinerSeats; i++) {
            theaterSeats.add(getTheaterSeat("A" + i, SeatType.RECLINER));
        }

        //Regular
        for(int i = 1; i <= regularSeats; i++) {
            theaterSeats.add(getTheaterSeat("B" + i, SeatType.REGULAR));
        }

        return theaterSeats;
    }

    private TheaterSeats getTheaterSeat(String seatNumber, SeatType seatType) {
        return TheaterSeats.builder()
                .seatNumber(seatNumber)
                .seatType(seatType)
                .build();
    }

    public TheaterDTO getTheater(long id) {
        log.info("Searching Theater by id: {}", id);

        Optional<Theater> theater = theaterRepository.findById(id);

        if(theater.isEmpty()) {
            log.error("Theater not found for id: {}", id);
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        return Theater.toResource(theater.get());
    }
}
