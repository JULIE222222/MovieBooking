package com.cinema.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Booking {
     private Long bookingID;
     private LocalDate bookingDate;
     private Long totalPrice;
     private String paymentStatus;


     private ShowTime showTime;


}
