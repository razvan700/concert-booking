package com.example.concertbookingapplication.concert;

import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ConcertDeleteIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ConcertRepository concertRepository;

    @Test
    void deleteConcert_removesFromDatabase() throws Exception {

        Concert concert = new Concert();
        concert.setName("ToDelete");
        concert = concertRepository.save(concert);

        mockMvc.perform(delete("/concerts/{id}", concert.getId()))
                .andExpect(status().isNoContent());

        assertTrue(concertRepository.findById(concert.getId()).isEmpty());
    }
}

