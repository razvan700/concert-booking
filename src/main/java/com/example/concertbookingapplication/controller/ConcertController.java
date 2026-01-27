package com.example.concertbookingapplication.controller;


import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertPatchDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.dto.ConcertUpdateDto;
import com.example.concertbookingapplication.service.ConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {

        this.concertService = concertService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getConcerts() {

        List<ConcertResponseDto> concerts = concertService.findAll();

        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConcertById(@PathVariable UUID id) {

        ConcertResponseDto concert = concertService.getConcertById(id);

        return ResponseEntity.status(HttpStatus.OK).body(concert);
    }

    @PostMapping
    public ResponseEntity<?> addConcert(@RequestBody ConcertCreateDto dto) {

        ConcertResponseDto concertResponseDto = concertService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(concertResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConcert(@RequestBody ConcertUpdateDto dto, @PathVariable UUID id) {

        ConcertResponseDto concertResponseDto = concertService.update(dto, id);

        return ResponseEntity.status(HttpStatus.OK).body(concertResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConcertResponseDto> patchConcert(
            @PathVariable UUID id,
            @RequestBody ConcertPatchDto dto) {

        ConcertResponseDto concertResponseDto = concertService.patchConcert(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(concertResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable UUID id) {

        concertService.deleteConcert(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{concertId}/artists/{artistId}")
    public ResponseEntity<?> addConcertToArtist(@PathVariable UUID concertId, @PathVariable UUID artistId) {

        ConcertResponseDto  concertResponseDto = concertService.addArtistToConcert(concertId, artistId);

        return ResponseEntity.status(HttpStatus.CREATED).body(concertResponseDto);
    }
}
