package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConcertPatchDto {

    private String name;

    private String type;

    private LocalDateTime startTime;
}
