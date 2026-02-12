package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.TicketReservationResponseDto;
import com.example.concertbookingapplication.entity.Seat;
import com.example.concertbookingapplication.entity.TicketReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TicketReservationMapper {

    @Mapping(target = "reservationId", source = "id")
    @Mapping(target = "concertId", source = "concert.id")
    @Mapping(
            target = "seatIds",
            expression = "java(mapSeatIds(reservation.getSeats()))"
    )
    @Mapping(target = "status", source = "status")
    @Mapping(target = "expiresAt", source = "expiresAt")
    TicketReservationResponseDto toResponse(TicketReservation reservation);

    default List<UUID> mapSeatIds(List<Seat> seats) {
        if (seats == null) {
            return List.of();
        }
        return seats.stream()
                .map(Seat::getId)
                .toList();
    }
}
