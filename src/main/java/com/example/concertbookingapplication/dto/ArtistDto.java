package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public record ArtistDto() {

    private static UUID id;

    private static String name;
}
