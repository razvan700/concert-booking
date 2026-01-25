package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.ArtistCreateDto;
import com.example.concertbookingapplication.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "id", ignore = true)
    Artist toEntity(ArtistCreateDto artist);

}
