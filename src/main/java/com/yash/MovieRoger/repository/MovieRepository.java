package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);

    public Movie findByTitle(String title);
}
