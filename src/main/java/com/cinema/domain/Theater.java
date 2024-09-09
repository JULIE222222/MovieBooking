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
    @Column(name = "screen_num") // 수정 확인: 컬럼 이름을 명시적으로 매핑
    private Long screenNum;



}
