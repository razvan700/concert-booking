package com.example.concertbookingapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "artists")
    private Set<Concert> concerts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist other = (Artist) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Artist() {
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Set<Concert> getConcerts() {

        return concerts;
    }

    public void setConcerts(Set<Concert> concerts) {

        this.concerts = concerts;
    }
}
