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

    // 모든 영화 pk 가져오기
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // 영화 pk 찾기
   /* public Movie getmovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("영화를 찾을 수 없습니다."));
    }*/


    public Movie findById(Long movieId) {
        return movieRepository.findByMovieID(movieId);
    }
}
