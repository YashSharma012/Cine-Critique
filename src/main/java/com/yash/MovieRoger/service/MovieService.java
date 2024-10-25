package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.MovieDTO;
import com.yash.MovieRoger.model.Movie;
import com.yash.MovieRoger.repository.CacheRepository;
import com.yash.MovieRoger.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CacheRepository cacheRepository;

    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie movie = Movie.toEntity(movieDTO);
        if(movieRepository.existsByTitle(movie.getTitle())) {
            return Movie.toResource(movie);
        }
        movieRepository.save(movie);
        log.info("Added New Movie {}", movie.toString());
        return Movie.toResource(movie);
    }

    public MovieDTO getMovie(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isEmpty()) {
            throw new EntityNotFoundException("Movie not found:" + id);
        }

        return Movie.toResource(movie.get());
    }

    public MovieDTO getMovie(String title) {
        Movie cachedMovie = cacheRepository.get(title.toLowerCase());
        if(cachedMovie != null) {
            MovieDTO resource = Movie.toResource(cachedMovie);
            return resource;
        }

        Movie movie = movieRepository.findByTitle(title);
        if(Objects.isNull(movie)) {
            throw new EntityNotFoundException("Movie not found:" + title);
        }
        if(movie.getSearchCount() == null){
            movie.setSearchCount(0L);
        }
        movie.setSearchCount(movie.getSearchCount() + 1);
        movieRepository.save(movie);
        cacheRepository.set(movie);
        return Movie.toResource(movie);
    }

    public List<MovieDTO> getTopMovies() {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        List<Movie> movies = movieRepository.getTopMovies();
        for (Movie movie : movies) {
            movieDTOList.add(Movie.toResource(movie));
        }
        return movieDTOList;
    }
}
