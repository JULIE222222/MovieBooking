package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@RequiredArgsConstructor
@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieID;
    private String title;
    private String director;

    @Column(columnDefinition = "TEXT")
    private String cast;
    private LocalDate releaseDate;
    private String rating;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String posterURL;

}
