package com.example.concertbookingapplication.mapper;

import com.example.concertbookingapplication.dto.ArtistCreateDto;
import com.example.concertbookingapplication.dto.ArtistPatchDto;
import com.example.concertbookingapplication.dto.ArtistResponseDto;
import com.example.concertbookingapplication.dto.ArtistUpdateDto;
import com.example.concertbookingapplication.entity.Artist;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "concerts", ignore = true)
    Artist toEntity(ArtistCreateDto artist);

    ArtistResponseDto toDto(Artist artist);

    void updateEntityFromDto(ArtistUpdateDto dto, @MappingTarget Artist artist);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name")
    void patchEntityFromDto(ArtistPatchDto dto, @MappingTarget Artist artist);
}
