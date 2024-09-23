package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Booking")
public class Booking {

     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "booking_id")
     private Long bookingID;

     private LocalDate bookingDate;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id")
     private Member member;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "movie_id")
     private Movie movie;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "show_time_id")
     private ShowTime showTime;

     @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 여러 좌석을 가질 수 있도록 변경
     @JoinColumn(name = "booking_id")
     private List<Seats> seats;
}
