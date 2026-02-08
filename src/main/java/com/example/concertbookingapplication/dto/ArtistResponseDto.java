package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ArtistResponseDto {

    private UUID id;

    private String name;

    private Long version;
}
