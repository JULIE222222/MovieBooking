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

import java.util.ArrayList;
import java.util.List;

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
        //상영관 목록
        List<Theater> theaters = new ArrayList<>();
        theaters.add(new Theater(1L, 1L));
        theaters.add(new Theater(2L, 2L));
        theaters.add(new Theater(3L, 3L));

        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        model.addAttribute("showTime", new ShowTime());
        return "uploadInfo/showtimeInfoForm";
    }

    @PostMapping("/showtimeInfoForm")
    public String submitShowtimeInfo(@ModelAttribute ShowTime showTime) {
        showTimeService.saveShowTime(showTime);
        return "redirect:/showtime/showtimeInfoForm";
    }

    // 영화 선택 후 상영 시간 정보를 업데이트하기 위한 엔드포인트 추가
    @PostMapping("/updateShowTime")
    public String updateShowTime(@RequestParam("movieId") Long movieId, Model model) {
        // 영화 ID로 상영 시간 정보를 조회하는 로직을 추가
        List<ShowTime> showTimes = showTimeRepository.findByMovie_MovieID(movieId);
        model.addAttribute("showTimes", showTimes);
        return "uploadInfo/showtimeTimeList"; // 상영 시간 목록을 보여주는 뷰 이름
    }

    //상영시간 정보를 조회하고 반환하기
    @GetMapping("/getShowTimes")
    @ResponseBody
    public List<ShowTime> getShowTimes(@RequestParam(value = "movieId") Long movieId, @RequestParam (value = "date") String showDate) {
        // 영화 ID와 날짜에 따라 상영 시간 정보를 조회하고 반환
        return showTimeRepository.findByMovie_MovieIDAndShowDate(movieId, showDate);
    }
}