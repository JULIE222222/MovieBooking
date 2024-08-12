package com.cinema.domain;

public class Movie {
    private Long movie_id;
    private String title;
    private String poster_url;

    public Long getmovie_id() {
        return movie_id;
    }

    public void setmovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }
}
