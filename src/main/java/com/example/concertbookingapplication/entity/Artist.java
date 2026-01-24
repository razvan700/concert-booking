package com.example.concertbookingapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
public class Artist {

    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    public Artist() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
