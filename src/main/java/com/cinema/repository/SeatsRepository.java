package com.cinema.repository;

import com.cinema.domain.Movie;
import com.cinema.domain.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository extends JpaRepository<Seats,Long> {

}

