package com.cinema.service;

import com.cinema.domain.Booking;
import com.cinema.domain.Member;
import com.cinema.domain.Seats;
import com.cinema.domain.ShowTime;
import com.cinema.dto.BookingDTO;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MemberRepository;
import com.cinema.repository.SeatsRepository;
import com.cinema.repository.ShowTimeRepository;
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
    private final ShowTimeRepository showTimeRepository;
    private final SeatsRepository seatsRepository;
    private final MemberRepository memberRepository;

    public Long saveBooking(BookingDTO bookingDTO) {
        // ShowTime 찾기
        ShowTime showTime = showTimeRepository.findById(bookingDTO.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));

        // 좌석 리스트 찾기
        List<Seats> seats = seatsRepository.findAllBySeatIDIn(bookingDTO.getSeats());

        // Member 찾기
        Member member = memberRepository.findById(bookingDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Booking 객체 생성 및 설정
        Booking booking = new Booking();
        booking.setShowTime(showTime);
        booking.setSeats(seats);
        booking.setMember(member);
        booking.setBookingDate(LocalDate.now());

        // 저장 후 Booking ID 반환
        bookingRepository.save(booking);
        return booking.getBookingID();
    }
}
