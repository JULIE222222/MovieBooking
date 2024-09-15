package com.cinema.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Payments {
    private Long paymentId;
    private Long amount;
    private LocalDate paymentDate;
    private String paymentMethod;

    private Booking bookingInfo;

    private Member member;
}
