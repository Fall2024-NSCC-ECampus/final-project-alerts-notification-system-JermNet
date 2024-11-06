package com.example.firestation;

import com.example.firestation.controller.PersonController;
import com.example.firestation.model.FireStation;
import com.example.firestation.repository.FireStationRepository;
import com.example.firestation.service.FireStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This tests sending valid data using Mockito. This test is successful, so the test works.
@WebMvcTest
public class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationRepository fireStationRepository;

    @MockBean
    private FireStationService fireStationService;

    @MockBean
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addFireStation_SuccessfulCreation() throws Exception {
        FireStation fireStation = new FireStation(1, 4, "123 Main St");
        String fireStationJson = objectMapper.writeValueAsString(fireStation);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fireStationJson))
                .andExpect(status().is2xxSuccessful());
    }
}
