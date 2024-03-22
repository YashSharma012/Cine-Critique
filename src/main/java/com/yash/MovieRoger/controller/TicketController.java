package com.yash.MovieRoger.controller;

import com.yash.MovieRoger.dto.BookingDTO;
import com.yash.MovieRoger.dto.TicketDTO;
import com.yash.MovieRoger.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<TicketDTO> bookTicket(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(ticketService.bookTicket(bookingDTO));
    }

    @GetMapping("/{ID}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }
}
