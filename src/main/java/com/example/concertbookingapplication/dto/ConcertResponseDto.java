package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ConcertResponseDto {

    private UUID id;

    private String name;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<UUID> artistIds;
}
