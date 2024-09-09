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
    public Map<String, List<ShowTime>> getShowTimes(@RequestParam(value = "movieId") Long movieId,
                                                    @RequestParam(value = "date") String showDate) {
        List<ShowTime> showTimes = showTimeRepository.findByMovie_MovieIDAndShowDate(movieId, showDate);

        // 상영관별로 상영 시간을 그룹화
        Map<String, List<ShowTime>> groupedShowTimes = new HashMap<>();
        for (ShowTime showTime : showTimes) {
            String screenNum = String.valueOf(showTime.getTheater().getScreenNum());
            groupedShowTimes.computeIfAbsent(screenNum, k -> new ArrayList<>()).add(showTime);
        }

        return groupedShowTimes; // 상영관별로 그룹화된 상영 시간 반환
    }
}