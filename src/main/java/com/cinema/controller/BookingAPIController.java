package com.cinema.controller;

import com.cinema.dto.BookingDTO;
import com.cinema.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/booking", method = RequestMethod.POST)
public class BookingAPIController {

      @Autowired
    private BookingService bookingService ;

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody BookingDTO bookingDTO) {
        System.out.println("Received booking data: " + bookingDTO);

        // DTO의 필드가 null인지 확인
        if (bookingDTO.getMovieId() == null || bookingDTO.getShowTimeId() == null) {
            return ResponseEntity.badRequest().body(null); // 잘못된 요청 처리
        }

        Long bookingId = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(bookingId); // 예약 ID를 응답으로 반환
    }

}