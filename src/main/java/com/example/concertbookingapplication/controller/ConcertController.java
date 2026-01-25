package com.example.concertbookingapplication.controller;


import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.service.ConcertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        Optional<ConcertResponseDto> concert = concertService.getConcertById(id);
        if (concert.isPresent()) {
            return ResponseEntity.ok(concert.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addConcert(@RequestBody ConcertCreateDto dto) {

        ConcertResponseDto concertResponseDto = concertService.save(dto);
        return ResponseEntity.ok(concertResponseDto);
    }

    @PutMapping
    public ResponseEntity<?> updateConcert(@RequestBody ConcertCreateDto dto) {

        ConcertResponseDto concertResponseDto = concertService.save(dto);
        return ResponseEntity.ok(concertResponseDto);
    }
}
