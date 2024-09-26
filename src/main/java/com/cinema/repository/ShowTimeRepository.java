package com.cinema.repository;

import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    List<ShowTime> findByMovie_MovieIDAndShowDate(Long movieId, LocalDate showDate);

    List<ShowTime> findByMovie_MovieID(Long movieId);

    // Movie의 ID를 참조할 때는 movieID를 사용합니다.
    List<ShowTime> findByShowDateAndMovie_MovieID(LocalDate showDate, Long movieId);
}
