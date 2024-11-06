package com.example.personinfo;

import com.example.personinfo.controller.MedicationController;
import com.example.personinfo.controller.PersonController;
import com.example.personinfo.model.Person;
import com.example.personinfo.repository.MedicationRepository;
import com.example.personinfo.repository.PersonRepository;
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
    private PersonController personController;

    @MockBean
    private MedicationController medicationController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addPerson_SuccessfulCreation() throws Exception {
        Person person = new Person("John", "Doe", "1234 Main St", "123-456-7890", 30);
        String personJson = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/personinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().is2xxSuccessful());
    }
}
