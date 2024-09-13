package com.cinema.domain.form;

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

}
