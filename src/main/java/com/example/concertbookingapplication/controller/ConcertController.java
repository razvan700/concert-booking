package com.example.concertbookingapplication.controller;


import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.service.ConcertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(concertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConcertById(@PathVariable UUID id) {
        Optional<Concert> concert = concertService.findById(id);
        if (concert.isPresent()) {
            return ResponseEntity.ok(concert.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addConcert(@RequestParam String name) {

        Concert concert = new Concert();
        concert.setName(name);
        concertService.save(concert);
        return ResponseEntity.ok().build();
    }
}
