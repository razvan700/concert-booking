package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.dto.TicketReservationCreateDto;
import com.example.concertbookingapplication.dto.TicketReservationResponseDto;
import com.example.concertbookingapplication.service.TicketReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class TicketReservationController {

    private final TicketReservationService ticketReservationService;

    public TicketReservationController(TicketReservationService ticketReservationService) {
        this.ticketReservationService = ticketReservationService;
    }

    @PostMapping
    public ResponseEntity<TicketReservationResponseDto> createReservation(
            @RequestBody TicketReservationCreateDto dto) {

        TicketReservationResponseDto response =
                ticketReservationService.createReservation(dto);

        return ResponseEntity
                .created(URI.create("/reservations/" + response.getReservationId()))
                .body(response);
    }

    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<TicketReservationResponseDto> confirmReservation(
            @PathVariable UUID reservationId) {

        TicketReservationResponseDto response =
                ticketReservationService.confirmReservation(reservationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<TicketReservationResponseDto> cancelReservation(
            @PathVariable UUID reservationId) {

        TicketReservationResponseDto response =
                ticketReservationService.cancelReservation(reservationId);

        return ResponseEntity.ok(response);
    }
}

