package com.cinema.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class ShowtimeDTO {

    private Long theaterID;
    private LocalDate showDate;
    private String screenNum;
    private String startTime;
    private String endTime;
}
