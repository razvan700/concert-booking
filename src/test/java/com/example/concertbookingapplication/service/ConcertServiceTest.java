package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.mapper.ConcertMapper;
import com.example.concertbookingapplication.repository.ArtistRepository;
import com.example.concertbookingapplication.repository.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    ConcertRepository concertRepository;

    @Mock
    ConcertMapper concertMapper;

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    ConcertService concertService;

    @Test
    void save_callsMapperAndRepository() {

        ConcertCreateDto dto = new ConcertCreateDto();
        dto.setName("Test");

        Concert concert = new Concert();
        concert.setName("Test");

        when(concertMapper.toEntity(dto)).thenReturn(concert);
        when(concertRepository.save(concert)).thenReturn(concert);

        concertService.save(dto);

        verify(concertMapper).toEntity(dto);
        verify(concertRepository).save(concert);
    }


    @Test
    void addArtistToConcert_addsRelationOnOwningSide() {

        UUID concertId = UUID.randomUUID();
        UUID artistId = UUID.randomUUID();

        Concert concert = new Concert();
        concert.setId(concertId);

        Artist artist = new Artist();
        artist.setId(artistId);

        when(concertRepository.findById(concertId))
                .thenReturn(Optional.of(concert));

        when(artistRepository.findById(artistId))
                .thenReturn(Optional.of(artist));

        concertService.addArtistToConcert(concertId, artistId);

        assertTrue(concert.getArtists().contains(artist));
    }
}
