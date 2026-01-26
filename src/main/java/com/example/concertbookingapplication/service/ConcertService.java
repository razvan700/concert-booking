package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.mapper.ConcertMapper;
import com.example.concertbookingapplication.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    public ConcertService(ConcertRepository concertRepository, ConcertMapper concertMapper) {

        this.concertRepository = concertRepository;
        this.concertMapper = concertMapper;
    }

    public List<ConcertResponseDto> findAll() {

        List<Concert> concerts = concertRepository.findAll();
        return concerts.stream()
                .map(concertMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ConcertResponseDto> getConcertById(UUID id) {

        Optional<Concert> concert = concertRepository.findById(id);
        return concert.map(concertMapper::toResponse);
    }

    public ConcertResponseDto save(ConcertCreateDto concert){

        Concert concertToBeSaved = concertMapper.toEntity(concert);
        concertRepository.save(concertToBeSaved);

        return concertMapper.toResponse(concertToBeSaved);
    }

    public Optional<ConcertResponseDto> update(ConcertCreateDto concert, UUID id) {

        Optional<Concert> concertToBeUpdated = concertRepository.findById(id);
        if(concertToBeUpdated.isPresent()){
            concertToBeUpdated.get().setName(concert.getName());
            concertRepository.save(concertToBeUpdated.get());

            return Optional.of(concertMapper.toResponse(concertToBeUpdated.get()));
        }

        return Optional.empty();
    }
}
