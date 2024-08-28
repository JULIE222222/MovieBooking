package com.cinema.service;

import com.cinema.domain.ShowTime;
import com.cinema.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    public ShowTime saveShowTime(ShowTime showTime){
        return showTimeRepository.save(showTime);
    }


}
