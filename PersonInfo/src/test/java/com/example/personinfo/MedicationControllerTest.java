package com.example.personinfo;

import com.example.personinfo.controller.PersonController;
import com.example.personinfo.model.Medication;
import com.example.personinfo.repository.MedicationRepository;
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
public class MedicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Despite saying they're not being used, I do still need these
    @MockBean
    private MedicationRepository medicationRepository;

    @MockBean
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addMedication_SuccessfulCreation() throws Exception {
        Medication medication = new Medication(1, "Penicillin", "55mg");
        String medicationJson = objectMapper.writeValueAsString(medication);

        mockMvc.perform(post("/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicationJson))
                .andExpect(status().is2xxSuccessful());
    }
}
