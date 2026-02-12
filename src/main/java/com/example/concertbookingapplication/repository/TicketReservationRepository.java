package com.example.concertbookingapplication.repository;

import com.example.concertbookingapplication.entity.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketReservationRepository extends JpaRepository<TicketReservation, UUID> {

    @Query("""
    select s.id
    from TicketReservation r
    join r.seats s
    where s.id in :seatIds
    and (
        (r.status = 'ACTIVE' and r.expiresAt > CURRENT_TIMESTAMP)
        or r.status = 'CONFIRMED'
    )
""")
    List<UUID> findTakenSeatIds(@Param("seatIds") List<UUID> seatIds);

    @Modifying
    @Query("""
    update TicketReservation r
    set r.status = 'EXPIRED'
    where r.status = 'ACTIVE'
    and r.expiresAt <= CURRENT_TIMESTAMP
""")
    void expireOldReservations();

}
