package com.cinema.repository;

import com.cinema.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    Movie save(Movie movie);
    Optional<Movie> findBymovie_id(Long movie_id);
    Optional<Movie> findByTitle(String title);
    List<Movie> findAll();
}
