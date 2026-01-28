package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.dto.*;
import com.example.concertbookingapplication.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        List<ArtistResponseDto> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable UUID id) {

        ArtistResponseDto artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<?> addArtist(@RequestBody ArtistCreateDto dto) {

        ArtistResponseDto created = artistService.saveArtist(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@RequestBody ArtistUpdateDto dto, @PathVariable UUID id) {

        ArtistResponseDto artistResponseDto = artistService.updateArtist(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(artistResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchArtist(
            @PathVariable UUID id,
            @RequestBody ArtistPatchDto dto) {

        System.out.println("PATCH DTO name = " + dto.getName());

        ArtistResponseDto artistResponseDto = artistService.patchArtist(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(artistResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable UUID id) {

        artistService.deleteArtist(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
