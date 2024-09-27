package com.cinema.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ShowtimeDTO {

    private Long theaterID;
    private LocalDate showDate;
    private String screenNum;
    private LocalTime startTime;  // LocalTime으로 수정
    private LocalTime endTime;    // LocalTime으로 수정
    private Long showTimeId;      // showTimeId 추가
}
