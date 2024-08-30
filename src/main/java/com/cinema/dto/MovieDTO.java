package com.cinema.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class MovieDTO {

    private String title;
    private MultipartFile pic;
    private String director;
    private String cast;
    private LocalDate releaseDate;
    private String rating;
    private String description;
    private String posterURL;
}
