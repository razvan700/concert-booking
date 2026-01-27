package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.*;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artists", ignore = true)
    Concert toEntity(ConcertCreateDto dto);

    @Mapping(
            target = "artistIds",
            expression = "java(mapArtistIds(concert.getArtists()))"
    )
    ConcertResponseDto toResponse(Concert concert);

    void updateEntityFromDto(ConcertUpdateDto dto, @MappingTarget Concert concert);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name")
    void patchEntityFromDto(ConcertPatchDto dto, @MappingTarget Concert concert);

    default List<UUID> mapArtistIds(Set<Artist> artists) {
        if (artists == null) {
            return List.of();
        }
        return artists.stream()
                .map(Artist::getId)
                .toList();
    }
}
