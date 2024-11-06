package com.example.firestation;

import com.example.firestation.controller.PersonController;
import com.example.firestation.model.FireStation;
import com.example.firestation.model.Person;
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
public class PersonControllerTest {

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
    public void addPerson_SuccessfulCreation() throws Exception {
        FireStation fireStation = new FireStation(1, 4, "123 Main St");
        Person person = new Person("John", "Doe", "1234 Main St", "123-456-7890", 30, 1);
        String personJson = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().is2xxSuccessful());
    }
}
