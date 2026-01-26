package com.example.concertbookingapplication.dto;

import java.util.UUID;

public class ConcertCreateDto {

    private UUID id;

    private String name;

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }
}
