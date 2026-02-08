package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConcertUpdateDto {

    private String name;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
