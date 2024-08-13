package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieID;
    private String title;
    private String director;
    private String cast;
    private Date releaseDate;
    private int rating;
    private String description;
    private String posterURL;

}
