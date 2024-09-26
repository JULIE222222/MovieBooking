package com.cinema.controller;


import com.cinema.domain.ShowTime;
import com.cinema.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowTimeAPIController {

    private final ShowTimeRepository showTimeRepository;


    // 상영시간 정보를 조회하고 반환하기
    @GetMapping
    public ResponseEntity<Map<String, List<ShowTime>>> getShowTimes(
            @RequestParam(value = "movieId") Long movieId,
            @RequestParam(value = "date") LocalDate showDate) {

        List<ShowTime> showTimes = showTimeRepository.findByMovie_MovieIDAndShowDate(movieId, showDate);

        if (showTimes.isEmpty()) {
            return ResponseEntity.notFound().build(); // 데이터가 없을 경우 404 응답
        }

        // 상영관별로 상영 시간을 그룹화
        Map<String, List<ShowTime>> groupedShowTimes = new HashMap<>();
        for (ShowTime showTime : showTimes) {
            String screenNum = String.valueOf(showTime.getTheater().getScreenNum());
            groupedShowTimes.computeIfAbsent(screenNum, k -> new ArrayList<>()).add(showTime);
        }

        return ResponseEntity.ok(groupedShowTimes); // 상영관별로 그룹화된 상영 시간 반환
    }
}
