package com.cinema.service;

import com.cinema.domain.Movie;
import com.cinema.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor //생성자 생성 어노테이션 (생성자 주입)
public class MovieService{

    private final MovieRepository movieRepository;

    @Transactional
    public Long save(Movie movie) {
        movieRepository.save(movie);
        return movie.getMovieID();
    }


    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findByMovieID(Long MovieID) {
        return movieRepository.findByMovieID(MovieID);
    }


}
