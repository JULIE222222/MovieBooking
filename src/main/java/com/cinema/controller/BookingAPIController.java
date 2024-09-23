package com.cinema.controller;

import com.cinema.domain.Booking;
import com.cinema.domain.Movie;
import com.cinema.domain.Seats;
import com.cinema.domain.ShowTime;
import com.cinema.dto.BookingDTO;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.SeatsRepository;
import com.cinema.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingAPIController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository; // 영화 Repository 주입

    @Autowired
    private ShowTimeRepository showTimeRepository; // 상영시간 Repository 주입

    @Autowired
    private SeatsRepository seatsRepository; // 좌석 Repository 주입

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Movie movie = movieRepository.findById(bookingDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        ShowTime showTime = showTimeRepository.findById(bookingDTO.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));

        // 메서드 이름 변경에 따라 수정
        List<Seats> selectedSeats = seatsRepository.findAllBySeatIDIn(bookingDTO.getSeats());

        Booking booking = new Booking();
        booking.setMovie(movie);
        booking.setShowTime(showTime);
        booking.setSeats(selectedSeats); // List<Seats>로 설정
        booking.setBookingDate(LocalDate.now());

        bookingRepository.save(booking);
        return ResponseEntity.ok(booking);
    }
}