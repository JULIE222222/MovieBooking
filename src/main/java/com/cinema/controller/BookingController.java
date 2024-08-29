package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        // 쇼타임 목록 가져오기
        List<ShowTime> showTimes = showTimeService.getAllShowTimes();

        // 모델에 영화 목록 추가
        model.addAttribute("movies", movies);
        // 모델에 쇼타임 목록 추가
        model.addAttribute("showTimes", showTimes);

        // 콘솔 출력 (디버깅 용도)
        System.out.println(movies);
        System.out.println(showTimes);

        // booking.html로 이동
        return "booking";
    }

    //영화관 좌석 선택하기
    @GetMapping("/selectSeat")
    public String SelectSeat (){


        return "/movies/selectSeat";
    }

}
