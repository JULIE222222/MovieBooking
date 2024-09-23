package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Seats")
@RequiredArgsConstructor
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "seat_id")
    private Long seatID;

    private String seatRow; //좌석 열
    private String seatNum; //좌석 행

    // 생성자에서 좌석 번호 설정
    public Seats(String seatRow, String seatNum) {
        this.seatRow = seatRow;
        this.seatNum = seatNum;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;

}
