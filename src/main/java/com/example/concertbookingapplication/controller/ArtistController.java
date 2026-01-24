package com.example.concertbookingapplication.controller;

import com.example.concertbookingapplication.dto.ArtistDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {

        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<?> addArtist(@RequestBody Artist artist) {

        artistService.saveArtist(artist);
        return ResponseEntity.ok(artist);
    }
}
