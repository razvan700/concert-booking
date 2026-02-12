package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.TicketReservationCreateDto;
import com.example.concertbookingapplication.dto.TicketReservationResponseDto;
import com.example.concertbookingapplication.entity.Seat;
import com.example.concertbookingapplication.entity.TicketReservation;
import com.example.concertbookingapplication.enums.ReservationStatus;
import com.example.concertbookingapplication.mapper.TicketReservationMapper;
import com.example.concertbookingapplication.repository.ConcertRepository;
import com.example.concertbookingapplication.repository.SeatRepository;
import com.example.concertbookingapplication.repository.TicketReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class TicketReservationService {

    private final TicketReservationRepository ticketReservationRepository;
    private final SeatRepository seatRepository;
    private final ConcertRepository concertRepository;
    private final TicketReservationMapper ticketReservationMapper;

    public TicketReservationService(
            TicketReservationRepository ticketReservationRepository,
            SeatRepository seatRepository,
            ConcertRepository concertRepository,
            TicketReservationMapper ticketReservationMapper
    ) {
        this.ticketReservationRepository = ticketReservationRepository;
        this.seatRepository = seatRepository;
        this.concertRepository = concertRepository;
        this.ticketReservationMapper = ticketReservationMapper;
    }

    @Transactional
    public TicketReservationResponseDto createReservation(TicketReservationCreateDto dto) {

        List<Seat> seats =
                seatRepository.findAllByIdForUpdate(dto.getSeatIds());

        if (seats.size() != dto.getSeatIds().size()) {
            throw new IllegalArgumentException("Some seats not found");
        }

        for (Seat seat : seats) {
            if (!seat.getConcert().getId().equals(dto.getConcertId())) {
                throw new IllegalArgumentException(
                        "Seat does not belong to given concert"
                );
            }
        }

        Instant now = Instant.now();
        System.out.println("JVM NOW: " + now);

        List<UUID> takenSeats =
                ticketReservationRepository.findTakenSeatIds(
                        dto.getSeatIds()
                );


        if (!takenSeats.isEmpty()) {
            throw new IllegalStateException("Some seats are already reserved");
        }

        TicketReservation reservation = new TicketReservation();
        reservation.setConcert(
                concertRepository.getReferenceById(dto.getConcertId())
        );
        reservation.setCustomerName(dto.getCustomerName());
        reservation.setSeats(seats);
        reservation.setReservationTime(now);
        reservation.setExpiresAt(now.plus(Duration.ofMinutes(15)));
        reservation.setStatus(ReservationStatus.ACTIVE);

        TicketReservation saved =
                ticketReservationRepository.save(reservation);

        return ticketReservationMapper.toResponse(saved);
    }

    @Transactional
    public TicketReservationResponseDto confirmReservation(UUID reservationId) {

        TicketReservation reservation =
                ticketReservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Reservation not found")
                        );

        Instant now = Instant.now();

        if (reservation.getStatus() != ReservationStatus.ACTIVE ||
                reservation.getExpiresAt().isBefore(now)) {
            throw new IllegalStateException("Reservation expired");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);

        return ticketReservationMapper.toResponse(reservation);
    }

    @Transactional
    public TicketReservationResponseDto cancelReservation(UUID reservationId) {

        TicketReservation reservation =
                ticketReservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Reservation not found")
                        );

        reservation.setStatus(ReservationStatus.CANCELLED);

        return ticketReservationMapper.toResponse(reservation);
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void expireOldReservations() {

        ticketReservationRepository.expireOldReservations();
    }
}
