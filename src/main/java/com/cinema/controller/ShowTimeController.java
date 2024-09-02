package com.cinema.controller;
import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/showtime")
@RequiredArgsConstructor
public class ShowTimeController {

    private final MovieService movieService;
    private final ShowTimeService showTimeService;

    @GetMapping("/showtimeInfoForm")
    public String showtimeInfoForm(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("showTime", new ShowTime());
        return "uploadInfo/showtimeInfoForm";
    }

    @PostMapping("/showtimeInfoForm")
    public String submitShowtimeInfo(@ModelAttribute ShowTime showTime) {
        showTimeService.saveShowTime(showTime);
        return "redirect:/uploadInfo/showtimeInfoForm";
    }
}