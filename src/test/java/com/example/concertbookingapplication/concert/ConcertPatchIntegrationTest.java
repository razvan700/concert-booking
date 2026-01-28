package com.example.concertbookingapplication.concert;


import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ConcertPatchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcertRepository concertRepository;

    @Test
    void patchConcert_updatesOnlyProvidedFields() throws Exception {

        Concert concert = new Concert();
        concert.setName("Geneva2015");
        concert.setType("FESTIVAL");
        concert.setStartTime(LocalDateTime.of(2026, 3, 21, 19, 30));
        concert.setEndTime(LocalDateTime.of(2026, 3, 21, 22, 0));

        concert = concertRepository.save(concert);

        String json = """
        {
          "name": "Geneva2016"
        }
        """;

        mockMvc.perform(patch("/concerts/{id}", concert.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Concert patched = concertRepository.findById(concert.getId()).orElseThrow();

        assertEquals("Geneva2016", patched.getName());
        assertEquals("FESTIVAL", patched.getType());
        assertEquals(LocalDateTime.of(2026, 3, 21, 19, 30), patched.getStartTime());
        assertEquals(LocalDateTime.of(2026, 3, 21, 22, 0), patched.getEndTime());
    }
}
