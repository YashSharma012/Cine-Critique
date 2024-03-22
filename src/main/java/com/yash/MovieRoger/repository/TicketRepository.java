package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
