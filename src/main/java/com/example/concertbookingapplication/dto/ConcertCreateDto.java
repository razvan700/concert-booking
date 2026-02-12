package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ConcertCreateDto {

    private String name;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int capacity;
}
