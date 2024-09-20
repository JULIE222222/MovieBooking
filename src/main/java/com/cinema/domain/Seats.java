package com.cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long seatNum; //좌석 행

    // 생성자에서 좌석 번호 설정
    public Seats (String row, Long i) {
        this.seatRow = seatRow;
        this.seatNum = seatNum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "showtime_id")
    @JsonIgnore
    private ShowTime showTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "theater_id")
    @JsonIgnore
    private Theater theater;

}
