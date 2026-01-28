package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ConcertUpdateIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ConcertRepository concertRepository;

    @Test
    void putConcert_replacesAllFields() throws Exception {

        Concert concert = new Concert();
        concert.setName("Old");
        concert.setType("CLUB");
        concert.setStartTime(LocalDateTime.of(2026, 1, 1, 18, 0));
        concert.setEndTime(LocalDateTime.of(2026, 1, 1, 20, 0));
        concert = concertRepository.save(concert);

        String json = """
        {
          "name": "New",
          "type": "FESTIVAL",
          "startTime": "2026-03-21T19:30",
          "endTime": "2026-03-21T22:00"
        }
        """;

        mockMvc.perform(put("/concerts/{id}", concert.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Concert updated = concertRepository.findById(concert.getId()).orElseThrow();

        assertEquals("New", updated.getName());
        assertEquals("FESTIVAL", updated.getType());
        assertEquals(LocalDateTime.of(2026,3,21,19,30), updated.getStartTime());
        assertEquals(LocalDateTime.of(2026,3,21,22,0), updated.getEndTime());
    }
}

