package com.cinema.service;

import com.cinema.domain.Theater;
import com.cinema.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater findById(Long theaterID) {
        Optional<Theater> theaterOptional = theaterRepository.findById(theaterID);
        if (theaterOptional.isPresent()) {
            return theaterOptional.get();
        } else {
            throw new RuntimeException("Theater not found with ID: " + theaterID);
        }
    }
}
