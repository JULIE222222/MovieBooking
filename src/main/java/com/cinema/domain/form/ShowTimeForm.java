package com.cinema.domain.form;

import com.cinema.domain.Movie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ShowTimeForm {

    private String hidden_poster;
    private String hidden_title;
    private LocalDate hidden_date;
    private LocalTime hidden_time;
    private String  hidden_movieId;
    private String hidden_showTimeId;


}
