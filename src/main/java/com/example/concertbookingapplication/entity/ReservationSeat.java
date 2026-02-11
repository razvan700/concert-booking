package com.example.concertbookingapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class ReservationSeat {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private TicketReservation reservation;

    @ManyToOne(optional = false)
    private Seat seat;
}
