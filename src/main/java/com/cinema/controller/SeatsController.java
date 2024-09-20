package com.cinema.controller;

import com.cinema.domain.Seats;
import com.cinema.service.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class SeatsController {

    private final SeatsService seatsService;

    @GetMapping("/settingSeats")
    public String settingSeats(Model model){
        List<String> seatRows = Arrays.asList("A", "B", "C", "D", "E"); // 예시로 A, B, C 열
        Long seatsPerRow = 10L; // 각 열에 10개 좌석

        List<Seats> seatsList = seatsService.settingSeats(seatRows, seatsPerRow);
        model.addAttribute("seats", seatsList);

        return "select-seat";
    }
}
