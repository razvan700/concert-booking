package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TicketReservationResponseDto {

    private UUID reservationId;

    private UUID concertId;

    private String customerName;

    private List<UUID> seatIds;

    private String status;

    private LocalDateTime expiresAt;
}

