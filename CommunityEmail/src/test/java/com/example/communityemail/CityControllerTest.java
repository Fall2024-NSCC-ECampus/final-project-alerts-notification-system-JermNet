package com.example.communityemail;

import com.example.communityemail.controller.PersonController;
import com.example.communityemail.model.City;
import com.example.communityemail.repository.CityRepository;
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
public class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addCity_SuccessfulCreation() throws Exception {
        City city = new City(1, 4, "City", "B#A-123");
        String cityJson = objectMapper.writeValueAsString(city);

        mockMvc.perform(post("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cityJson))
                .andExpect(status().is2xxSuccessful());
    }
}
