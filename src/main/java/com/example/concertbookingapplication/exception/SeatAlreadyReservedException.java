package com.example.concertbookingapplication.exception;

import java.util.UUID;

public class SeatAlreadyReservedException extends ApplicationException {

    public SeatAlreadyReservedException() {
        super(
                "One or more selected seats are already reserved",
                409
        );
    }
}
