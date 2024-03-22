package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.MovieDTO;
import com.yash.MovieRoger.model.Movie;
import com.yash.MovieRoger.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @GetMapping("/title")
    public ResponseEntity<MovieDTO> getMovie(@RequestParam("title") String title) {
        return ResponseEntity.ok(movieService.getMovie(title));
    }
}
