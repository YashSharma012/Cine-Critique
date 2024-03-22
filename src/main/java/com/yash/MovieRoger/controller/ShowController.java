package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.ShowDTO;
import com.yash.MovieRoger.model.Show;
import com.yash.MovieRoger.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<ShowDTO> addShow(@RequestBody ShowDTO showDTO) {
        return new ResponseEntity<>(showService.createShow(showDTO), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShowDTO>> searchShow(
            @RequestParam(name = "city",required = true) String city,
            @RequestParam(name = "movieName", required = false) String movieName,
            @RequestParam(name = "theaterName", required = false) String theaterName) {
        return  ResponseEntity.ok(showService.searchShow(movieName, city, theaterName));
    }
}
