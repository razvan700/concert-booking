package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.TicketReservationCreateDto;
import com.example.concertbookingapplication.entity.Seat;
import com.example.concertbookingapplication.entity.TicketReservation;
import com.example.concertbookingapplication.enums.ReservationStatus;
import com.example.concertbookingapplication.repository.ConcertRepository;
import com.example.concertbookingapplication.repository.SeatRepository;
import com.example.concertbookingapplication.repository.TicketReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketReservationService {

    private final TicketReservationRepository ticketReservationRepository;
    private final SeatRepository seatRepository;
    private final ConcertRepository concertRepository;

    public TicketReservationService(
            TicketReservationRepository ticketReservationRepository,
            SeatRepository seatRepository,
            ConcertRepository concertRepository
    ) {
        this.ticketReservationRepository = ticketReservationRepository;
        this.seatRepository = seatRepository;
        this.concertRepository = concertRepository;
    }

    @Transactional
    public TicketReservation createReservation(TicketReservationCreateDto dto) {

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

        List<UUID> takenSeats =
                ticketReservationRepository.findTakenSeatIds(
                        dto.getSeatIds(),
                        LocalDateTime.now()
                );

        if (!takenSeats.isEmpty()) {
            throw new IllegalStateException(
                    "Some seats are already reserved"
            );
        }

        TicketReservation reservation = new TicketReservation();
        reservation.setConcert(
                concertRepository.getReferenceById(dto.getConcertId())
        );
        reservation.setCustomerName(dto.getCustomerName());
        reservation.setSeats(seats);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        reservation.setStatus(ReservationStatus.ACTIVE);

        return ticketReservationRepository.save(reservation);
    }

    @Transactional
    public void confirmReservation(UUID reservationId) {

        TicketReservation reservation =
                ticketReservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Reservation not found")
                        );

        if (reservation.getStatus() != ReservationStatus.ACTIVE ||
                reservation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Reservation expired");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
    }

    @Transactional
    public void cancelReservation(UUID reservationId) {

        TicketReservation reservation =
                ticketReservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Reservation not found")
                        );

        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
