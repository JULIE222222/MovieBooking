package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    public final MovieService movieService;
    public final ShowTimeService showTimeService;

    //영화 리스트 띄우기
    @GetMapping("/booking")
    public String getAllShowTimesAndMovies(Model model) {
        // 영화 목록 가져오기
        List<Movie> movies = movieService.getAllMovies();
        // 모델에 영화 목록 추가
        model.addAttribute("movies", movies);
        // 콘솔 출력 (디버깅 용도)
        System.out.println(movies);
        // booking.html로 이동
        return "booking";
    }

    //영화관 좌석 선택하기
    @GetMapping("/selectSeat")
    public String selectSeat(@RequestParam Theater theater, Model model) {

        return "/movies/selectSeat";
    }

}
