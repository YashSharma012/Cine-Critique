package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.TheaterDTO;
import com.yash.MovieRoger.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    TheaterService theaterService;

    @GetMapping("/{id}")
    public ResponseEntity<TheaterDTO> getUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(theaterService.getTheater(id));
    }

    @PostMapping("/add")
    public ResponseEntity<TheaterDTO> addUser(@RequestBody TheaterDTO theaterDTO) {
        return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
    }
}
