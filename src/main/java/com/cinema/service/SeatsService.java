package com.cinema.service;

import com.cinema.domain.Seats;
import com.cinema.domain.ShowTime;
import com.cinema.repository.SeatsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class SeatsService {

    private final SeatsRepository seatsRepository; // 인스턴스 변수로 선언

    // 좌석 초기화 메서드
    public void initializeSeats() {
        List<Seats> seats = new ArrayList<>();
        List<String> rows = Arrays.asList("A", "B", "C", "D", "E");
        Long seatsPerRow = 10L;

        for (String row : rows) {
            for (Long number = 1L; number <= seatsPerRow; number++) {
                Seats seat = new Seats();
                seat.setSeatRow(row);
                seat.setSeatNum(String.valueOf(number));
                seats.add(seat);
            }
        }

        // 데이터베이스에 좌석 저장
        seatsRepository.saveAll(seats);
    }

    // 애플리케이션 시작 시 좌석 초기화
    @PostConstruct
    public void init() {
        if (seatsRepository.count() == 0) { // 좌석이 없을 경우에만 초기화
            initializeSeats(); // 좌석 초기화 메서드 호출
        }
    }

    // 모든 좌석 정보를 가져오는 메서드
    public List<Seats> getAllSeats() {
        return seatsRepository.findAll(); // 모든 좌석 정보를 반환

    }

}
