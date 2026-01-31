package com.example.concertbookingapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Concert {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "concert_artist",
            joinColumns = @JoinColumn(name = "concert_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();

    @Column(name = "concert_type")
    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Version
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concert)) return false;
        Concert other = (Concert) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public Concert() {}

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

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
