package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CreateConcertIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcertRepository concertRepository;

    @Test
    void createConcert_persistsAllFields() throws Exception {

        String json = """
        {
          "name": "Geneva2015",
          "type": "FESTIVAL",
          "startTime": "2026-03-21T19:30",
          "endTime": "2026-03-21T22:00"
        }
        """;

        mockMvc.perform(post("/concerts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        Concert concert = concertRepository.findAll().get(0);

        assertEquals("Geneva2015", concert.getName());
        assertEquals("FESTIVAL", concert.getType());
        assertNotNull(concert.getStartTime());
        assertNotNull(concert.getEndTime());
    }
}
