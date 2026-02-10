package com.example.concertbookingapplication.repository;

import com.example.concertbookingapplication.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
