package com.example.concertbookingapplication.controller;

import com.example.concertbookingapplication.dto.ArtistCreateDto;
import com.example.concertbookingapplication.dto.ArtistResponseDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.mapper.ArtistMapper;
import com.example.concertbookingapplication.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    private final ArtistMapper artistMapper;

    public ArtistController(ArtistService artistService, ArtistMapper artistMapper) {

        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {

        List<ArtistResponseDto> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable UUID id) {

        Optional<Artist> artist = artistService.getArtistById(id);
        if (!artist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ArtistResponseDto artistResponseDto = artistMapper.toDto(artist.get());
        return ResponseEntity.ok(artistResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> addArtist(@RequestBody ArtistCreateDto dto) {

        ArtistResponseDto artistResponseDto = artistService.saveArtist(dto);
        return ResponseEntity.ok(artistResponseDto);
    }
}
