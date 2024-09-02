package com.cinema.controller;


import com.cinema.domain.ShowTime;
import com.cinema.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowTimeAPIController {
    private final ShowTimeService showTimeService;

    @GetMapping
    public ResponseEntity<List<ShowTime>> getShowTimes(@RequestParam("date") String date) {
        List<ShowTime> showTimes = showTimeService.getShowTimesByDate(date);
        return ResponseEntity.ok(showTimes);
    }
}
