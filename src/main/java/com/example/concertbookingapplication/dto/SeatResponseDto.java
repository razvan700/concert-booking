package com.example.concertbookingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SeatResponseDto {

    private UUID id;
    private int seatNo;
}

