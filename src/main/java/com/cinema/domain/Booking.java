package com.cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Booking")
public class Booking {

     @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
     @Column (name = "booking_id")
     private Long bookingID;
     private LocalDate bookingDate;
     private Long totalPrice;
     private String paymentStatus;

     @ManyToOne (fetch = FetchType.LAZY)
     @JoinColumn (name = "showTime_id")
     @JsonIgnore
     private ShowTime showTime;


}
