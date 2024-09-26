package com.cinema.service;

import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import com.cinema.repository.ShowTimeRepository;
import com.cinema.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    public List<ShowTime> getShowTimesByDateAndMovie(LocalDate date, Long movieId) {
        return showTimeRepository.findByShowDateAndMovie_MovieID(date, movieId);
    }

    public void saveShowTime(ShowTime showTime) {
    }
}
