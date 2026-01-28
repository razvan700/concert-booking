package com.example.concertbookingapplication.artist;

import com.example.concertbookingapplication.entity.Artist;
import com.example.concertbookingapplication.entity.Concert;
import com.example.concertbookingapplication.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArtistCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void createArtist_persistsAllFields() throws Exception {

        String json = """
        {
          "name": "MichaelJackson"
        }
        """;

        mockMvc.perform(post("/artists")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        Artist artist = artistRepository.findAll().get(0);

        assertEquals("MichaelJackson", artist.getName());
    }
}
