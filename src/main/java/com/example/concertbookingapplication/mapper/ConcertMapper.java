package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.entity.Concert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artists", ignore = true)
    Concert toEntity(ConcertCreateDto dto);

    ConcertResponseDto toResponse(Concert concert);
}
