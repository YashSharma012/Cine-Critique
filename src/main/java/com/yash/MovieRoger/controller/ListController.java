package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.MovieDTO;
import com.yash.MovieRoger.dto.ShowDTO;
import com.yash.MovieRoger.dto.TheaterDTO;
import com.yash.MovieRoger.model.Movie;
import com.yash.MovieRoger.model.Show;
import com.yash.MovieRoger.model.Theater;
import com.yash.MovieRoger.service.MovieService;
import com.yash.MovieRoger.service.ShowService;
import com.yash.MovieRoger.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    TheaterService theaterService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowService showService;



    @GetMapping("/theaters")
    public ResponseEntity<List<Theater>> theater(@RequestParam(name = "city") String city) {
        return new ResponseEntity<>(theaterService.getTheaterByCity(city), HttpStatus.OK);
    }

    @GetMapping("/shows")
    public ResponseEntity<List<ShowDTO>> shows(@RequestParam(name = "theater") String theater) {
        return new ResponseEntity<>(showService.getShowByTheater(theater), HttpStatus.OK);
    }

    @GetMapping("/topMovies")
    public ResponseEntity<List<MovieDTO>> topMovies() {
        return new ResponseEntity<>(movieService.getTopMovies(), HttpStatus.OK);
    }
}
