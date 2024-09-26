package com.cinema.controller;

import com.cinema.domain.*;
import com.cinema.domain.form.MovieForm;
import com.cinema.domain.form.ShowTimeForm;
import com.cinema.service.BookingService;
import com.cinema.service.MovieService;
import com.cinema.service.SeatsService;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class BookingController {

    public final MovieService movieService;
    public final ShowTimeService showTimeService;
    public final SeatsService seatsService;
    public final BookingService bookingService;

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

    //선택한 데이터 좌석선택하는 view로 가져오기
    @PostMapping("/prebooking")
    public String getPreBookingData(@ModelAttribute ShowTimeForm showTimeForm, Model model){
        String hidden_poster = showTimeForm.getHidden_poster();
        String hidden_title = showTimeForm.getHidden_title();
        LocalDate hidden_date = showTimeForm.getHidden_date();
        LocalTime hidden_time = showTimeForm.getHidden_time();
        String hidden_movieId = showTimeForm.getHidden_movieId();
        String hidden_showTimeId = showTimeForm.getHidden_showTimeId();

        //가져온 정보를 모델에 추가하여 view에 전달
        model.addAttribute("hidden_poster",hidden_poster);
        model.addAttribute("hidden_title", hidden_title);
        model.addAttribute("hidden_date", hidden_date);
        model.addAttribute("hidden_time", hidden_time);
        model.addAttribute("hidden_movieId", hidden_movieId);
        model.addAttribute("hidden_showTimeId", hidden_showTimeId);

        System.out.println("hidden_time = " + hidden_time);
        System.out.println("hidden_title = " + hidden_title);
        System.out.println("hidden_date = " + hidden_date);
        System.out.println("hidden_poster = " + hidden_poster);
        System.out.println("Movie ID: " + hidden_movieId);
        System.out.println("Show Time ID: " + hidden_showTimeId);

        return "/reserve/selectSeat";
    }

}
