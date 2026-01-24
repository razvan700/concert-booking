package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(UUID id) {
        return artistRepository.findById(id);
    }

    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }
}
