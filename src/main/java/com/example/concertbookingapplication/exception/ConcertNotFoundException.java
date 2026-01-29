package com.example.concertbookingapplication.exception;

import java.util.UUID;

public class ConcertNotFoundException extends ApplicationException {

    public ConcertNotFoundException(UUID id) {
        super(
                "Concert not found with id: " + id,
                404
        );
    }
}
