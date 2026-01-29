package com.example.concertbookingapplication.exception;

import java.util.UUID;

public class ArtistNotFoundException extends  ApplicationException {
    public ArtistNotFoundException(UUID id) {

        super("Artist with id " + id + " not found!");
    }
}
