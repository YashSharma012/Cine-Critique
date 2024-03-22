package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.ShowDTO;
import com.yash.MovieRoger.enums.SeatType;
import com.yash.MovieRoger.model.*;
import com.yash.MovieRoger.repository.MovieRepository;
import com.yash.MovieRoger.repository.ShowRepository;
import com.yash.MovieRoger.repository.ShowSeatRepository;
import com.yash.MovieRoger.repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private static final Logger log = LoggerFactory.getLogger(ShowService.class);
    @Autowired
    ShowRepository showRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    public ShowDTO createShow(ShowDTO showDTO) {
        Optional<Movie> movie = movieRepository.findById(showDTO.getMovieId());
        if(movie.isEmpty()) throw new EntityNotFoundException("Movie not found with ID:" + showDTO.getMovieId());

        Optional<Theater> theater = theaterRepository.findById(showDTO.getTheaterId());
        if(theater.isEmpty()) throw new EntityNotFoundException("Theater not found with ID:" + showDTO.getTheaterId());

        log.info("Adding New Show {}", showDTO);

        Show show = Show.toEntity(showDTO);

        show.setMovie(movie.get());
        show.setTheater(theater.get());
        show.setSeats(generateShowSeats(show.getTheater().getSeats(), show));

        for (ShowSeat seatsEntity : show.getSeats()) {
            seatsEntity.setShow(show);
        }

        show = showRepository.save(show);

        return Show.toResource(show);
    }

    private List<ShowSeat> generateShowSeats(List<TheaterSeats> theaterSeatEntity, Show show) {
        List<ShowSeat> showSeatEntity = new ArrayList<>();

        for(TheaterSeats theaterSeats : theaterSeatEntity) {
            ShowSeat showSeat =
                    ShowSeat.builder()
                            .seatNumber(theaterSeats.getSeatNumber())
                            .seatType(theaterSeats.getSeatType())
                            .rate(setSeatRate(theaterSeats.getSeatType()))
                            .build();
            showSeatEntity.add(showSeat);
        }
        return showSeatRepository.saveAll(showSeatEntity);
    }

    private int setSeatRate(SeatType seatType) {
        return switch (seatType) {
            case REGULAR -> 100;
            case RECLINER -> 150;
        };
    }

    public List<ShowDTO> searchShow(String movieName, String cityName, String theaterName) {
        if(!StringUtils.hasText(cityName))
            return new ArrayList<>();
        List<Show> shows = new ArrayList<>();
        if(StringUtils.hasText(movieName)) {
            shows = showRepository.findByMovieNameAndCity(movieName, cityName);
        }else if (StringUtils.hasText(theaterName)) {
            shows = showRepository.findByTheaterNameAndCity(theaterName, cityName);
        }else {
            shows = showRepository.findByCity(cityName);
        }
        if(CollectionUtils.isEmpty(shows))
            return new ArrayList<>();
        else
           return shows.stream().map(Show::toResource).collect(Collectors.toList());
    }
}
