package com.cinema.controller;
import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import com.cinema.repository.ShowTimeRepository;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import com.cinema.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/showtime")
@RequiredArgsConstructor
public class ShowTimeController {

    private final MovieService movieService;
    private final ShowTimeService showTimeService;
    private final ShowTimeRepository showTimeRepository;
    private final TheaterService theaterService;

    @GetMapping("/showtimeInfoForm")
    public String showtimeInfoForm(Model model) {

        List<Movie> movies = movieService.getAllMovies();
        List<Theater> theaters = theaterService.getAllTheaters();

        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        model.addAttribute("showTime", new ShowTime());
        return "uploadInfo/showtimeInfoForm";
    }

    @PostMapping("/showtimeInfoForm")
    public String submitShowtimeInfo(@ModelAttribute ShowTime showTime) {
        // 디버깅을 위한 로그 출력
        System.out.println("Theater ID: " + showTime.getTheater().getTheaterID());
        System.out.println("Screen Number: " + showTime.getTheater().getScreenNum());

        showTimeService.saveShowTime(showTime);
        return "redirect:/showtime/showtimeInfoForm";
    }



}