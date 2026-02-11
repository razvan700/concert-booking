package com.example.concertbookingapplication.repository;

import com.example.concertbookingapplication.entity.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketReservationRepository extends JpaRepository<TicketReservation, UUID> {
}
