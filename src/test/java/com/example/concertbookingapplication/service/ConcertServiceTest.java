package com.example.concertbookingapplication.service;

import com.example.concertbookingapplication.dto.ConcertCreateDto;
import com.example.concertbookingapplication.dto.ConcertPatchDto;
import com.example.concertbookingapplication.dto.ConcertResponseDto;
import com.example.concertbookingapplication.dto.ConcertUpdateDto;
import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.exception.ArtistNotFoundException;
import com.example.concertbookingapplication.exception.ConcertNotFoundException;
import com.example.concertbookingapplication.mapper.ConcertMapper;
import com.example.concertbookingapplication.repository.ArtistRepository;
import com.example.concertbookingapplication.repository.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private ConcertMapper concertMapper;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void findAll_returnsMappedDtos() {
        Concert concert = new Concert();
        ConcertResponseDto dto = new ConcertResponseDto();

        when(concertRepository.findAll()).thenReturn(List.of(concert));
        when(concertMapper.toResponse(concert)).thenReturn(dto);

        List<ConcertResponseDto> result = concertService.findAll();

        assertEquals(1, result.size());
        verify(concertMapper).toResponse(concert);
    }

    @Test
    void getConcertById_returnsDto() {
        UUID id = UUID.randomUUID();
        Concert concert = new Concert();
        ConcertResponseDto dto = new ConcertResponseDto();

        when(concertRepository.findById(id)).thenReturn(Optional.of(concert));
        when(concertMapper.toResponse(concert)).thenReturn(dto);

        ConcertResponseDto result = concertService.getConcertById(id);

        assertNotNull(result);
    }

    @Test
    void getConcertById_throwsWhenMissing() {
        UUID id = UUID.randomUUID();

        when(concertRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class,
                () -> concertService.getConcertById(id));
    }

    @Test
    void save_persistsAndReturnsDto() {
        ConcertCreateDto createDto = new ConcertCreateDto();
        Concert concert = new Concert();
        ConcertResponseDto responseDto = new ConcertResponseDto();

        when(concertMapper.toEntity(createDto)).thenReturn(concert);
        when(concertMapper.toResponse(concert)).thenReturn(responseDto);

        ConcertResponseDto result = concertService.save(createDto);

        verify(concertRepository).save(concert);
        assertNotNull(result);
    }

    @Test
    void update_updatesExistingConcert() {
        UUID id = UUID.randomUUID();
        ConcertUpdateDto updateDto = new ConcertUpdateDto();
        Concert concert = new Concert();
        ConcertResponseDto responseDto = new ConcertResponseDto();

        when(concertRepository.findById(id)).thenReturn(Optional.of(concert));
        when(concertMapper.toResponse(concert)).thenReturn(responseDto);

        ConcertResponseDto result = concertService.update(updateDto, id);

        verify(concertMapper).updateEntityFromDto(updateDto, concert);
        assertNotNull(result);
    }

    @Test
    void update_throwsWhenConcertMissing() {
        UUID id = UUID.randomUUID();
        ConcertUpdateDto updateDto = new ConcertUpdateDto();

        when(concertRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class,
                () -> concertService.update(updateDto, id));
    }

    @Test
    void patchConcert_updatesFields() {
        UUID id = UUID.randomUUID();
        ConcertPatchDto patchDto = new ConcertPatchDto();
        Concert concert = new Concert();
        ConcertResponseDto responseDto = new ConcertResponseDto();

        when(concertRepository.findById(id)).thenReturn(Optional.of(concert));
        when(concertMapper.toResponse(concert)).thenReturn(responseDto);

        ConcertResponseDto result = concertService.patchConcert(id, patchDto);

        verify(concertMapper).patchEntityFromDto(patchDto, concert);
        verify(concertRepository).save(concert);
        assertNotNull(result);
    }

    @Test
    void deleteConcert_deletesById() {
        UUID id = UUID.randomUUID();

        concertService.deleteConcert(id);

        verify(concertRepository).deleteById(id);
    }

    @Test
    void addArtistToConcert_addsRelationOnOwningSide() {
        UUID concertId = UUID.randomUUID();
        UUID artistId = UUID.randomUUID();

        Concert concert = new Concert();
        Artist artist = new Artist();

        concert.setId(concertId);
        artist.setId(artistId);

        when(concertRepository.findById(concertId))
                .thenReturn(Optional.of(concert));
        when(artistRepository.findById(artistId))
                .thenReturn(Optional.of(artist));
        when(concertMapper.mapArtistIds(any()))
                .thenReturn(List.of(artistId));

        ConcertResponseDto result =
                concertService.addArtistToConcert(concertId, artistId);

        assertTrue(concert.getArtists().contains(artist));
        assertTrue(artist.getConcerts().contains(concert));
        assertEquals(1, result.getArtistIds().size());
    }

    @Test
    void addArtistToConcert_throwsWhenArtistMissing() {
        UUID concertId = UUID.randomUUID();
        UUID artistId = UUID.randomUUID();

        when(concertRepository.findById(concertId))
                .thenReturn(Optional.of(new Concert()));
        when(artistRepository.findById(artistId))
                .thenReturn(Optional.empty());

        assertThrows(ArtistNotFoundException.class,
                () -> concertService.addArtistToConcert(concertId, artistId));
    }

    @Test
    void removeArtistFromConcert_removesRelation() {
        UUID concertId = UUID.randomUUID();
        UUID artistId = UUID.randomUUID();

        Concert concert = new Concert();
        Artist artist = new Artist();

        concert.getArtists().add(artist);
        artist.getConcerts().add(concert);

        when(concertRepository.findById(concertId))
                .thenReturn(Optional.of(concert));
        when(artistRepository.findById(artistId))
                .thenReturn(Optional.of(artist));
        when(concertMapper.mapArtistIds(any()))
                .thenReturn(List.of());

        ConcertResponseDto result =
                concertService.removeArtistFromConcert(concertId, artistId);

        assertFalse(concert.getArtists().contains(artist));
        assertFalse(artist.getConcerts().contains(concert));
        assertTrue(result.getArtistIds().isEmpty());
    }
}
