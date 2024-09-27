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
        Long bookingId = bookingService.saveBooking(bookingDTO);
        return ResponseEntity.ok(bookingId); // 예약 ID를 응답으로 반환
    }

}