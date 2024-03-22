package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query(value = "select * from shows s, movies m, theaters t where s.movie_id=m.id and s.theater_id=t.id and m.title=? and t.city=?" , nativeQuery = true)
    List<Show> findByMovieNameAndCity(String movieName, String cityName);

    @Query(value = "select * from shows s, theaters t where s.theater_id=t.id and t.name=? and t.city=?", nativeQuery = true)
    List<Show> findByTheaterNameAndCity(String theaterName, String cityName);

    @Query(value= "select s.* from shows s, theaters t where s.theater_id=t.id and t.city=?",nativeQuery = true)
    List<Show> findByCity(String cityName);
}
