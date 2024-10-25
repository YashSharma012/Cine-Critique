package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.TheaterDTO;
import com.yash.MovieRoger.dto.TheaterUpdateDTO;
import com.yash.MovieRoger.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    TheaterService theaterService;

    @GetMapping("/{id}")
    public ResponseEntity<TheaterDTO> getTheater(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(theaterService.getTheater(id));
    }

    @PostMapping("/add")
    public ResponseEntity<TheaterDTO> addTheater(@RequestBody TheaterDTO theaterDTO) {
        return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
    }

    @PatchMapping("/edit")
    public ResponseEntity<TheaterDTO> editUser(@Valid @RequestBody TheaterUpdateDTO updateDTO) {
        return new ResponseEntity<>(theaterService.editTheater(updateDTO), HttpStatus.OK);
    }
}
