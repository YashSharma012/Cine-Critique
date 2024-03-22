package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.BookingDTO;
import com.yash.MovieRoger.dto.TicketDTO;
import com.yash.MovieRoger.exception.NotFountException;
import com.yash.MovieRoger.model.Show;
import com.yash.MovieRoger.model.ShowSeat;
import com.yash.MovieRoger.model.Ticket;
import com.yash.MovieRoger.model.User;
import com.yash.MovieRoger.repository.ShowRepository;
import com.yash.MovieRoger.repository.TicketRepository;
import com.yash.MovieRoger.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    public TicketDTO bookTicket(BookingDTO bookingDTO) {
        Optional<User> optionalUser = userRepository.findById(bookingDTO.getUserId());
        if(optionalUser.isEmpty()) {
            throw new NotFountException("User Not Found with ID: " + bookingDTO.getUserId() + " to Book Ticket");
        }

        Optional<Show> optionalShow = showRepository.findById(bookingDTO.getShowId());
        if(optionalShow.isEmpty()) {
            throw new NotFountException("Show Not Found with ID: " + bookingDTO.getShowId() + " to Book Ticket");
        }

        Set<String> requestedSeats = bookingDTO.getSeatsNumbers();
        System.out.println("Size of List " +  requestedSeats.size());

        List<ShowSeat> showSeatEntities = optionalShow.get().getSeats();
        System.out.println("Size of List " +  showSeatEntities.size());

        showSeatEntities =
                showSeatEntities
                        .stream()
                        .filter(seat -> seat.getSeatType().equals(bookingDTO.getSeatType())
                                && !seat.isBooked()
                                && requestedSeats.contains(seat.getSeatNumber()))
                        .collect(Collectors.toList());
        System.out.println("Size of List " +  showSeatEntities.size());

        if(showSeatEntities.size() != requestedSeats.size()) {
            throw new NotFountException("Seats not available for booking");
        }

        Ticket ticket =
                Ticket.builder()
                        .user(optionalUser.get())
                        .show(optionalShow.get())
                        .seats(showSeatEntities)
                        .build();

        double amount = 0.0;
        StringBuilder allottedSeats = new StringBuilder();

        for(ShowSeat showSeat: showSeatEntities) {
            showSeat.setBooked(true);
            showSeat.setBookedAt(new Date());
            showSeat.setTicket(ticket);

            amount += showSeat.getRate();

            allottedSeats.append(showSeat.getSeatNumber()).append(" ");
        }

        ticket.setAmount(amount);
        ticket.setAllottedSeats(allottedSeats.toString());

        if (CollectionUtils.isEmpty(optionalUser.get().getTicketEntities())) {
            optionalUser.get().setTicketEntities(new ArrayList<>());
        }

        optionalUser.get().getTicketEntities().add(ticket);

        if(CollectionUtils.isEmpty(optionalShow.get().getTickets())) {
            optionalShow.get().setTickets(new ArrayList<>());
        }
        optionalShow.get().getTickets().add(ticket);

        ticket = ticketRepository.save(ticket);

        return Ticket.toResource(ticket);
    }

    public TicketDTO getTicket(long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if(ticket.isEmpty()) {
            log.error("Ticket not Fount with ID: {}", id);
            throw new EntityNotFoundException("Ticket not Found with ID: " + id);
        }

        return Ticket.toResource(ticket.get());
    }
}
