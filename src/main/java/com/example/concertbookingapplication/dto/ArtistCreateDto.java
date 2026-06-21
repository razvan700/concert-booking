package com.example.concertbookingapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ArtistCreateDto {

    private String name;

    public ArtistCreateDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
