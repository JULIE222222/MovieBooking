package com.cinema.repository;

import com.cinema.domain.Movie;


import java.util.*;

public class MemoryMovieRepository implements MovieRepository{
    private  static Map<Long, Movie> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Movie save(Movie movie) {
        movie.setmovie_id(++sequence);
        store.put(movie.getmovie_id(), movie);
        return movie;
    }

    @Override
    public Optional<Movie> findBymovie_id(Long movie_id) {
        return Optional.ofNullable(store.get(movie_id));
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return store.values().stream()
                .filter(movie->movie.getTitle().equals(title))
                .findAny();
    }

    public void clearStore(){
        store.clear();
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }
}
