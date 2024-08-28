package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    public final MovieService movieService;

    //영화 리스트 띄우기
   @GetMapping("/booking")
    public String getAllMovies(Model model){
        List<Movie> movies = movieService.getAllMovies(); // 영화 목록 가져오기
        model.addAttribute("movies", movies); // 모델에 영화 목록 추가
        System.out.println(movies);
        return "booking"; // booking.html로 이동
    }

    //영화관 좌석 선택하기
    @GetMapping("/selectSeat")
    public String SelectSeat (){


        return "/movies/selectSeat";
    }

}
