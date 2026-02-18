package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.*;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.entity.Seat;
import com.example.concertbookingapplication.exception.ArtistNotFoundException;
import com.example.concertbookingapplication.exception.ConcertNotFoundException;
import com.example.concertbookingapplication.mapper.ConcertMapper;
import com.example.concertbookingapplication.repository.ArtistRepository;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;
    private final ArtistRepository artistRepository;

    public ConcertService(ConcertRepository concertRepository, ConcertMapper concertMapper, ArtistRepository artistRepository) {

        this.concertRepository = concertRepository;

        this.concertMapper = concertMapper;
        this.artistRepository = artistRepository;
    }

    @Cacheable("concertList")
    public List<ConcertResponseDto> findAll() {

        List<Concert> concerts = concertRepository.findAll();

        return concerts.stream()
                .map(concertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "concerts", key = "#id")
    public ConcertResponseDto getConcertById(UUID id) {

        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        return concertMapper.toResponse(concert);
    }

    @Transactional
    @CacheEvict(value = "concertList", allEntries = true)
    public ConcertResponseDto save(ConcertCreateDto dto) {

        Concert concert = concertMapper.toEntity(dto);
        concert.setCapacity(dto.getCapacity());

        for (int i = 1; i <= dto.getCapacity(); i++) {
            Seat seat = new Seat();
            seat.setSeatNo(i);
            seat.setConcert(concert);
            concert.getSeats().add(seat);
        }

        Concert saved = concertRepository.save(concert);

        return concertMapper.toResponse(saved);
    }

    public List<SeatResponseDto> getSeatsForConcert(UUID concertId) {

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalArgumentException("Concert not found"));

        return concert.getSeats()
                .stream()
                .map(seat -> new SeatResponseDto(seat.getId(), seat.getSeatNo()))
                .toList();
    }

    @Caching(evict = {
            @CacheEvict(value = "concerts", key = "#id"),
            @CacheEvict(value = "concertList", allEntries = true)
    })
    @Transactional
    public ConcertResponseDto update(ConcertUpdateDto concert, UUID id) {

        Concert concertToBeUpdated = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        concertMapper.updateEntityFromDto(concert, concertToBeUpdated);

        return concertMapper.toResponse(concertToBeUpdated);
    }

    @Caching(evict = {
            @CacheEvict(value = "concerts", key = "#id"),
            @CacheEvict(value = "concertList", allEntries = true)
    })
    @Transactional
    public ConcertResponseDto patchConcert(UUID id, ConcertPatchDto concertPatchDto) {

        Concert concertToBeUpdated = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        concertMapper.patchEntityFromDto(concertPatchDto, concertToBeUpdated);

        concertRepository.save(concertToBeUpdated);

        return concertMapper.toResponse(concertToBeUpdated);
    }

    @Caching(evict = {
            @CacheEvict(value = "concerts", key = "#id", beforeInvocation = true),
            @CacheEvict(value = "concertList", allEntries = true)
    })
    public void deleteConcert(UUID id) {

        concertRepository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "concerts", key = "#concertId"),
            @CacheEvict(value = "concertList", allEntries = true)
    })
    @Transactional
    public ConcertResponseDto addArtistToConcert(UUID concertId, UUID artistId) {

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotFoundException(concertId));

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));

        concert.getArtists().add(artist);

        artist.getConcerts().add(concert);

        List<UUID> artistIds = concertMapper.mapArtistIds(concert.getArtists());

        ConcertResponseDto concertResponseDto = new ConcertResponseDto();

        concertResponseDto.setId(concertId);

        concertResponseDto.setArtistIds(artistIds);

        concertResponseDto.setName(concert.getName());

        return concertResponseDto;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "concerts", key = "#concertId"),
            @CacheEvict(value = "concertList", allEntries = true)
    })
    public ConcertResponseDto removeArtistFromConcert(UUID concertId, UUID artistId) {

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotFoundException(concertId));

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));

        concert.getArtists().remove(artist);

        artist.getConcerts().remove(concert);

        List<UUID> artistIds = concertMapper.mapArtistIds(concert.getArtists());

        ConcertResponseDto concertResponseDto = new ConcertResponseDto();

        concertResponseDto.setId(concertId);

        concertResponseDto.setArtistIds(artistIds);

        concertResponseDto.setName(concert.getName());

        return concertResponseDto;
    }
}
