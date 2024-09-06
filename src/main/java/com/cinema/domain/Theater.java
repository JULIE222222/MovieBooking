package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Theater")
@RequiredArgsConstructor

public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "theater_id")
    private Long theaterID;
    private Long screenNum;

    // 하드코딩 데이터 생성을 위한 생성자 추가
    public Theater(Long theaterID, Long screenNum) {
        this.theaterID = theaterID;
        this.screenNum = screenNum;
    }

}
