package com.cinema.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seats {

    private Long seatID;
    private String seatRow;
    private Long seatNum;

    private ShowTime showTime;

    private Theater theater;

}
