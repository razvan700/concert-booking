package com.example.concertbookingapplication.repository;

import com.example.concertbookingapplication.entity.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketReservationRepository extends JpaRepository<TicketReservation, UUID> {
}
