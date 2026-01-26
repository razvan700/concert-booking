package com.example.concertbookingapplication.exception;

import java.util.UUID;

public class ConcertNotFoundException extends RuntimeException {
    public ConcertNotFoundException(UUID id) {

        super("Concert with id + " + id + "not found!");
    }
}
