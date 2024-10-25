package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);

    Movie findByTitle(String title);

    @Query(value = "select * from movies order by search_count desc limit 5", nativeQuery = true)
    List<Movie> getTopMovies();
}

