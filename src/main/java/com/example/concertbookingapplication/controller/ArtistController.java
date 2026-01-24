package com.example.concertbookingapplication.controller;

import com.example.concertbookingapplication.entity.Artist;
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

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {

        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable UUID id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        if (!artist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(artist.get());
    }

    @PostMapping
    public ResponseEntity<?> addArtist(@RequestParam String name) {

        Artist artist = new Artist();
        artist.setName(name);
        artistService.saveArtist(artist);
        return ResponseEntity.ok(artist);
    }
}
