package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
//    @Query("update theaters set ")
//    Optional<Theater> editTheater(Theater theater);

    @Query(value = "select * from theaters where lcase(city) = :city", nativeQuery = true)
    Optional<List<Theater>> findByCity(String city);
}
