package com.cinema.repository;

import com.cinema.domain.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
/*    List<Movie> findAll();

    Optional<Movie> findByMovieID(Long MovieID);
    Optional<Movie> findByTitle(String Title);*/

    Movie findByMovieID(Long MovieID);

    List<Movie> findTop4ByOrderByReleaseDateDesc();
}
