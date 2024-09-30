package com.cinema.controller;

import com.cinema.domain.Seats;
import com.cinema.service.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class SeatsController {

    private final SeatsService seatsService;

    // 영화관 좌석 선택하기 페이지
    @GetMapping("/selectSeat")
    public String selectSeat(Model model) {
        // 모든 좌석 정보를 모델에 추가
        List<Seats> seatsList = seatsService.getAllSeats();
        model.addAttribute("seats", seatsList);
        return "/reserve/selectSeat"; // 좌석 선택 페이지로 이동
    }

    /*@PostMapping("/selectSeat")
    public String setSelectSeat() {
        return "/reserve/selectSeat"; // 좌석 선택 후 다시 이동
    }*/

    // 모든 좌석 정보를 반환하는 API 엔드포인트
    @GetMapping("/api/seats")
    public ResponseEntity<List<Seats>> getSeats() {
        List<Seats> seats = seatsService.getAllSeats(); // 모든 좌석 정보를 반환
        return ResponseEntity.ok(seats); // JSON으로 반환
    }
}
