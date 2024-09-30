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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // 생성자 주입
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowTimeRepository showTimeRepository;
    private final SeatsRepository seatsRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService; // MemberService 주입 추가

    public Long createBooking(BookingDTO bookingDTO) {
        System.out.println("BookingDTO: " + bookingDTO);

        // ShowTime 조회
        ShowTime showTime = showTimeRepository.findById(bookingDTO.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found with ID: " + bookingDTO.getShowTimeId()));
        System.out.println("ShowTime found: " + showTime);

        // Member 조회
        Member member = memberService.nowMember(); // MemberService의 nowMember 호출
        System.out.println("member = " + member);

        // Seats 조회
        List<Seats> seats = seatsRepository.findAllBySeatIDIn(bookingDTO.getSeats());
        if (seats.isEmpty()) {
            throw new RuntimeException("Seats not found for IDs: " + bookingDTO.getSeats());
        }
        System.out.println("Seats found: " + seats);

        // Booking 생성 및 저장
        Booking booking = new Booking();
        booking.setShowTime(showTime);
        booking.setSeats(seats);
        booking.setMember(member);

        bookingRepository.save(booking);
        System.out.println("Booking saved: " + booking);
        return booking.getBookingID(); // 예약의 ID를 반환
    }
}
