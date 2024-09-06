package com.cinema.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShowtimeDTO {

    private Long theaterID;
    private String showDate;
    private String screenNum;
    private String startTime;
    private String endTime;
}
