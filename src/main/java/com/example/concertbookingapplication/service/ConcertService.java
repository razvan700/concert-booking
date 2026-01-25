package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {

        this.concertRepository = concertRepository;
    }

    public List<Concert> findAll() {

        return concertRepository.findAll();
    }

    public Optional<Concert> findById(UUID id) {

        return concertRepository.findById(id);
    }

    public Concert save(Concert concert){

        return concertRepository.save(concert);
    }
}
