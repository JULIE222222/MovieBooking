package com.cinema.form;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class MovieForm {

    private String title;
    private MultipartFile pic;
    private String director;
    private String cast;
    private LocalDate releaseDate;
    private String rating;
    private String description;
    private String posterURL;
}
