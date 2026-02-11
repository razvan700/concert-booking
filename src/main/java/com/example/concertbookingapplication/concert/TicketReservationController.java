package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.dto.TicketReservationCreateDto;
import com.example.concertbookingapplication.dto.TicketReservationResponseDto;
import com.example.concertbookingapplication.entity.TicketReservation;
import com.example.concertbookingapplication.service.TicketReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketReservationController {

    TicketReservationService ticketReservationService;

    TicketReservationController(TicketReservationService ticketReservationService) {
        this.ticketReservationService = ticketReservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<TicketReservationResponseDto> createReservation(
            @RequestBody TicketReservationCreateDto dto) {

        TicketReservation reservation =
                ticketReservationService.createReservation(dto);

        return ResponseEntity.ok(mapToResponse(reservation));
    }

}
