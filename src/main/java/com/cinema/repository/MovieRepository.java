package com.cinema.repository;

import com.cinema.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAll();

//    Optional<Movie> findByMovieID(Long MovieID);
    //Optional<Movie> findByTitle(String Title);

    Movie findByMovieID(Long MovieID);
}
