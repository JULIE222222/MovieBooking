package com.cinema.repository;

import com.cinema.domain.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    // Movie의 ID를 참조할 때는 movieID를 사용합니다.
    List<ShowTime> findByShowDateAndMovie_MovieID(LocalDate showDate, Long movieId);

    List<ShowTime> findByMovie_MovieIDAndShowDateAndShowTimeID(Long movieId, LocalDate showDate, Long showTimeId);

    List<ShowTime> findByMovie_MovieIDAndShowDate(Long movieId, LocalDate showDate);
}
