package com.cinema.repository;

import com.cinema.domain.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    List<ShowTime> findByScreenNum(Long screenNum);
    List<ShowTime> findByShowDate(String showDate);
    List<ShowTime> findByMovie_MovieIDAndShowDate(Long movieId, String showDate);
}
