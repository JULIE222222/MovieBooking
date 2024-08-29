package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class ShowTimeController {

    private final MovieService movieService;
    private final ShowTimeService showTimeService;

    @GetMapping("/showtimeInfoForm")
    public String showtimeInfoForm(Model model){
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("showTime", new ShowTime());
        return "uploadInfo/showtimeInfoForm";
    }

    @PostMapping("/showtimeInfoForm")
    public String submitShowtimeInfo(@ModelAttribute ShowTime showTime){
        showTimeService.saveShowTime(showTime);
        return "redirect:/showtimeInfoForm";
    }


    @RequestMapping("/api")
    @GetMapping("/getShowTimes")
    public ResponseEntity<List<ShowTime>> getShowTimes(@RequestParam("date") String date) {
        // date를 기반으로 상영 시간 목록을 가져오는 로직
        List<ShowTime> showTimes = showTimeService.getShowTimesByDate(date);
        return ResponseEntity.ok(showTimes);
        }
    }

