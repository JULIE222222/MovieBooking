package com.cinema.service;

import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import com.cinema.repository.ShowTimeRepository;
import com.cinema.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    public ShowTime saveShowTime(ShowTime showTime){
        return showTimeRepository.save(showTime);
    }

    public List<ShowTime> getShowTimesByDate(String showDate) {
        return showTimeRepository.findByShowDate(showDate);
    }

}
