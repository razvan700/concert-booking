package com.example.concertbookingapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Concert {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "concerts")
    private Set<Artist> artists = new HashSet<>();

    public Concert() {
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    public UUID getId() {

        return this.id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public Set<Artist> getArtists() {

        return this.artists;
    }

    public void setArtists(Set<Artist> artists) {

        this.artists = artists;
    }
}
