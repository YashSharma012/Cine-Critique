package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.ShowDTO;
import com.yash.MovieRoger.enums.SeatType;
import com.yash.MovieRoger.exception.InvalidShowTimeException;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        //You have to add show before 24 hours of it's date and time
        if(isShowTimeInThePast(showDTO.getShowTime())){
            throw new InvalidShowTimeException("Please enter a valid show time");
        }
        Optional<Movie> movie = movieRepository.findById(showDTO.getMovieId());
        if(movie.isEmpty()) throw new EntityNotFoundException("Movie not found with ID:" + showDTO.getMovieId());

        Optional<Theater> theater = theaterRepository.findById(showDTO.getTheaterId());
        if(theater.isEmpty()) throw new EntityNotFoundException("Theater not found with ID:" + showDTO.getTheaterId());

        log.info("Adding New Show {}", showDTO);

        Show show = Show.toEntity(showDTO);

        show.setMovie(movie.get());
        show.setTheater(theater.get());
        show.setSeats(generateShowSeats(show.getTheater().getSeats(), show, showDTO.getRegularSeatPrice(), showDTO.getReclinerSeatPrice()));

        for (ShowSeat seatsEntity : show.getSeats()) {
            seatsEntity.setShow(show);
        }

        show = showRepository.save(show);

        return Show.toResource(show);
    }

    private List<ShowSeat> generateShowSeats(List<TheaterSeats> theaterSeatEntity, Show show, int regularSeatPrice, int reclinerSeatPrice) {
        List<ShowSeat> showSeatEntity = new ArrayList<>();

        for(TheaterSeats theaterSeats : theaterSeatEntity) {
            ShowSeat showSeat =
                    ShowSeat.builder()
                            .seatNumber(theaterSeats.getSeatNumber())
                            .seatType(theaterSeats.getSeatType())
                            .rate(setSeatRate(theaterSeats.getSeatType(), regularSeatPrice, reclinerSeatPrice))
                            .build();
            showSeatEntity.add(showSeat);
        }
        return showSeatRepository.saveAll(showSeatEntity);
    }

    private int setSeatRate(SeatType seatType, int regularSeatPrice, int reclinerSeatPrice) {
        return switch (seatType) {
            case REGULAR -> regularSeatPrice;
            case RECLINER -> reclinerSeatPrice;
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

    public boolean isShowTimeInThePast(LocalDateTime showTime) {
        return showTime.isBefore(LocalDateTime.now().plusDays(1));
    }

    public List<ShowDTO> getShowByTheater(String theater) {
        List<Show> shows = showRepository.getShowByTheater(theater);
        List<ShowDTO> showDTOList = new ArrayList<>();
        for(Show show : shows) {
            showDTOList.add(Show.toResource(show));
        }
        return showDTOList;
    }
}
