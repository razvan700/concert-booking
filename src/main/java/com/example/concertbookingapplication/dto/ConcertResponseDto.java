package com.example.concertbookingapplication.dto;

import java.util.List;
import java.util.UUID;

public class ConcertResponseDto {

    private UUID id;

    private String name;

    private List<UUID> artistIds;

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public UUID getId() {

        return this.id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public List<UUID> getArtistIds() {
        return this.artistIds;
    }

    public void setArtistIds(List<UUID> artistIds) {
        this.artistIds = artistIds;
    }
}
