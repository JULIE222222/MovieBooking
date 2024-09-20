package com.cinema.service;

import com.cinema.domain.Seats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatsService {

    // 좌석 배치 생성 메서드: 열과 좌석 번호를 함께 설정
    public List<Seats> settingSeats(List<String> seatRows, Long seatsPerRow) {
        List<Seats> seats = new ArrayList<>();

        for (String row : seatRows) {   // 각 열에 대해
            for (Long i = 1L; i <= seatsPerRow; i++) {  // 각 열에 좌석 번호 부여
                Seats seat = new Seats(row, i);   // 좌석 열과 번호를 생성자에 전달
                seats.add(seat);  // 리스트에 좌석 추가
            }
        }
        return seats;

    }
}
