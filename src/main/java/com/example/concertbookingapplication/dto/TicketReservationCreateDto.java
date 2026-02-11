package com.example.concertbookingapplication.dto;

import com.example.concertbookingapplication.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TicketReservationCreateDto {

    private UUID concertId;

    private String customerName;

    private List<UUID> seatIds;
}

