package com.example.concertbookingapplication.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ConcertResponseDto {

    private UUID id;

    private String name;

    private String type;

    private LocalDateTime startTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    private LocalDateTime endTime;

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
