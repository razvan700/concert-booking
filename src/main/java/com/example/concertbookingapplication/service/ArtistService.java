package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.ArtistCreateDto;
import com.example.concertbookingapplication.dto.ArtistResponseDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.mapper.ArtistMapper;
import com.example.concertbookingapplication.repository.ArtistRepository;
import com.example.concertbookingapplication.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final ConcertRepository concertRepository;

    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper,
                         ConcertRepository concertRepository) {

        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.concertRepository = concertRepository;
    }

    public List<ArtistResponseDto> getAllArtists() {
        return artistRepository.findAll()
                .stream().map(artistMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Artist> getArtistById(UUID id) {

        return artistRepository.findById(id);
    }

    public ArtistResponseDto saveArtist(ArtistCreateDto artist) {

        Artist artistToBeSaved = artistMapper.toEntity(artist);
        artistRepository.save(artistToBeSaved);
        return artistMapper.toDto(artistToBeSaved);
    }
}
