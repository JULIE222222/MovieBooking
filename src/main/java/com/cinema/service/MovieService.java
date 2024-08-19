package com.cinema.service;

import com.cinema.domain.Movie;
import com.cinema.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor //생성자 생성 어노테이션 (생성자 주입)
public class MovieService{

    private final MovieRepository movieRepository;

    @Transactional
    // 영화 정보 저장
    public Long save(Movie movie) {
        movieRepository.save(movie);
        return movie.getMovieID();
    }

    // 모든 영화 pk 가져오기
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // pk값 찾기
    public Movie findById(Long movieId) {
        return movieRepository.findByMovieID(movieId);
    }

    // 개봉일 내림차순으로 4개의 데이터 불러오기(메인화면 영화리스트 띄우기)
    public List<Movie> get4Movies(){
        return movieRepository.findTop4ByOrderByReleaseDateDesc();
    }

}
