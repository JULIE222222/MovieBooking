package com.cinema.repository;

import com.cinema.domain.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seats,Long> {

    List<Seats> findAllBySeatIDIn(List<String> seats);
}


