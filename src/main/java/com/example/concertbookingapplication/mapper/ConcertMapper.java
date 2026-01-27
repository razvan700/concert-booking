package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.*;
import com.example.concertbookingapplication.entity.Concert;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artists", ignore = true)
    Concert toEntity(ConcertCreateDto dto);

    ConcertResponseDto toResponse(Concert concert);

    void updateEntityFromDto(ConcertUpdateDto dto, @MappingTarget Concert concert);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name")
    void patchEntityFromDto(ConcertPatchDto dto, @MappingTarget Concert concert);
}
