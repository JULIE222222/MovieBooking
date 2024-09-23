package com.cinema.repository;

import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    List<ShowTime> findByShowDate(String showDate);
    List<ShowTime> findByMovie_MovieIDAndShowDate(Long movieId, String showDate);

    List<ShowTime> findByMovie_MovieID(Long movieId);
    // Theater 엔티티 객체로 검색
    List<ShowTime> findByTheater(Theater theater);

    // 기본 키를 사용하여 검색
    List<ShowTime> findByTheater_TheaterID(Long theaterID);


}
