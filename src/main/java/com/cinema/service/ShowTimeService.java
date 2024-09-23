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
@Transactional
@RequiredArgsConstructor
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    // 상영 시간 저장
    public ShowTime saveShowTime(ShowTime showTime){
        return showTimeRepository.save(showTime);
    }

    // 상영 날짜로 상영 시간 목록 조회
    public List<ShowTime> getShowTimesByDate(String showDate) {
        LocalDate parsedDate = LocalDate.parse(showDate);
        return showTimeRepository.findByShowDate(String.valueOf(parsedDate));
    }

}
