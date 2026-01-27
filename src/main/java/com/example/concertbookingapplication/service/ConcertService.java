package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertPatchDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.dto.ConcertUpdateDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.exception.ArtistNotFoundException;
import com.example.concertbookingapplication.exception.ConcertNotFoundException;
import com.example.concertbookingapplication.mapper.ConcertMapper;
import com.example.concertbookingapplication.repository.ArtistRepository;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<ConcertResponseDto> findAll() {

        List<Concert> concerts = concertRepository.findAll();

        return concerts.stream()
                .map(concertMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ConcertResponseDto getConcertById(UUID id) {

        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        return concertMapper.toResponse(concert);
    }

    public ConcertResponseDto save(ConcertCreateDto concert){

        Concert concertToBeSaved = concertMapper.toEntity(concert);

        concertRepository.save(concertToBeSaved);

        return concertMapper.toResponse(concertToBeSaved);
    }

    public ConcertResponseDto update(ConcertUpdateDto concert, UUID id) {

        Concert concertToBeUpdated = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        concertMapper.updateEntityFromDto(concert, concertToBeUpdated);

        return concertMapper.toResponse(concertToBeUpdated);
    }

    public ConcertResponseDto patchConcert(UUID id, ConcertPatchDto concertPatchDto) {

        Concert concertToBeUpdated = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));

        concertMapper.patchEntityFromDto(concertPatchDto, concertToBeUpdated);

        concertRepository.save(concertToBeUpdated);

        return concertMapper.toResponse(concertToBeUpdated);
    }

    public void deleteConcert(UUID id) {

        concertRepository.deleteById(id);
    }

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
