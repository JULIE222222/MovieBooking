package com.cinema.service;

import com.cinema.domain.Booking;
import com.cinema.domain.Member;
import com.cinema.domain.Seats;
import com.cinema.domain.ShowTime;
import com.cinema.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // 생성자 주입
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(ShowTime showTime, List<Seats> seats, Member member) {
        Booking booking = new Booking();
        booking.setShowTime(showTime);
        booking.setSeats(seats); // 좌석 리스트 설정
        booking.setMember(member);
        booking.setBookingDate(LocalDate.now());
        return bookingRepository.save(booking); // 예약 정보 저장
    }
}
